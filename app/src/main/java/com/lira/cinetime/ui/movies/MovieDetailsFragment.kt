package com.lira.cinetime.ui.movies

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.lira.cinetime.R
import com.lira.cinetime.core.createDialog
import com.lira.cinetime.core.createProgressDialog
import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.databinding.FragmentMovieDetailsBinding
import com.lira.cinetime.presentation.movies.MovieDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val movieDetailsViewModel by viewModel<MovieDetailsViewModel>()
    private val dialog by lazy { createProgressDialog() }
    private val castAdapter by lazy { MovieCastAdapter() }

    private val args: MovieDetailsFragmentArgs by navArgs()

    private var isFavorite: Boolean = false
    private var isInToWatch: Boolean = false

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        movieDetailsViewModel.getMovieDetails(movieId)
        movieDetailsViewModel.checkIfFavorite(movieId)
        movieDetailsViewModel.checkIfInToWatch(movieId)
        binding.rvMovieCast.adapter = castAdapter
        collectData()
        setupAppBar()
    }

    private fun setupAppBar() {
        binding.movieDetailsToolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.movieDetails.collect {
                    when(it) {
                        MovieDetailsViewModel.State.Loading -> {
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                            dialog.show()
                        }
                        is MovieDetailsViewModel.State.Error -> {
                            dialog.dismiss()
                            createDialog {
                                setMessage(it.error.message)
                            }.show()
                        }
                        is MovieDetailsViewModel.State.Success -> {
                            dialog.dismiss()
                            setupUI(it.movieDetails)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.dbOperations.collectLatest {
                    when (it) {
                        MovieDetailsViewModel.DBState.Loading -> {
                            // nothing
                        }
                        is MovieDetailsViewModel.DBState.AddFavoriteFailure -> {
                            Snackbar
                                .make(binding.root, "${it.error.message}", Snackbar.LENGTH_LONG)
                                .show()
                            binding.movieDetailsToolbar.menu.findItem(R.id.favorite_list_button).icon =
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.favorite_border,
                                    null
                                )
                        }
                        is MovieDetailsViewModel.DBState.AddFavoriteSuccess -> {
                            Snackbar
                                .make(
                                    binding.root,
                                    "Adicionado aos favoritos!",
                                    Snackbar.LENGTH_LONG
                                )
                                .show()
                            isFavorite = true
                        }
                        is MovieDetailsViewModel.DBState.AddToWatchFailure -> {
                            Snackbar
                                .make(binding.root, "${it.error.message}", Snackbar.LENGTH_LONG)
                                .show()
                            binding.movieDetailsToolbar.menu.findItem(R.id.watch_list_button).icon =
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.playlist_add,
                                    null
                                )
                        }
                        is MovieDetailsViewModel.DBState.AddToWatchSuccess -> {
                            Snackbar
                                .make(
                                    binding.root,
                                    "Adicionado à lista \"Para assistir\"!",
                                    Snackbar.LENGTH_LONG
                                )
                                .show()
                            isInToWatch = true
                        }
                        is MovieDetailsViewModel.DBState.IsFavorite -> {
                            if (it.favorite) {
                                isFavorite = true
                                binding.movieDetailsToolbar.menu.findItem(R.id.favorite_list_button).icon =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.favorite,
                                        null
                                    )
                            }
                        }
                        is MovieDetailsViewModel.DBState.IsInToWatch -> {
                            if (it.inToWatch) {
                                isInToWatch = true
                                binding.movieDetailsToolbar.menu.findItem(R.id.watch_list_button).icon =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.playlist_remove,
                                        null
                                    )
                            }
                        }
                        is MovieDetailsViewModel.DBState.Error -> {
                            createDialog {
                                setMessage(it.error.message)
                            }.show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUI(movieDetails: MovieDetailsResponse) {
        binding.apply {
            // Menu
            movieDetailsToolbar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.favorite_list_button -> {
                        if(!isFavorite){
                            menuItem.icon = ResourcesCompat.getDrawable(resources, R.drawable.favorite, null)
                            movieDetailsViewModel.addToFavorites(
                                movieId = movieDetails.id,
                                title = movieDetails.title,
                                posterPath = movieDetails.posterPath
                            )
                        } else {
                            menuItem.icon = ResourcesCompat.getDrawable(resources, R.drawable.favorite_border, null)
                            movieDetailsViewModel.deleteFavoriteMovie(movieDetails.id)
                            isFavorite = false
                        }
                        true
                    }
                    R.id.watch_list_button -> {
                        if(!isInToWatch) {
                            menuItem.icon = ResourcesCompat.getDrawable(resources, R.drawable.playlist_remove, null)
                            movieDetailsViewModel.addToWatchList(
                                movieId = movieDetails.id,
                                title = movieDetails.title,
                                posterPath = movieDetails.posterPath
                            )
                        } else {
                            menuItem.icon = ResourcesCompat.getDrawable(resources, R.drawable.playlist_add, null)
                            movieDetailsViewModel.deleteMovieInToWatch(movieDetails.id)
                            isInToWatch = false
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }


            // Poster img
            val backdropPath: String = "https://image.tmdb.org/t/p/original/" + movieDetails.backdropPath
            Glide
                .with(root)
                .load(backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .into(ivMoviePosterDetails)

            tvMovieTitleDetails.text = movieDetails.title

            // Release year
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val date = LocalDate.parse(movieDetails.releaseDate)
                val year = date.year
                tvYearReleaseDetails.text = year.toString()
            }

            // Genres
            if(movieDetails.genres.isNotEmpty()) {
                when {
                    movieDetails.genres.size >= 3 -> {
                        val genres = movieDetails.genres[0].name + ", " + movieDetails.genres[1].name + ", " + movieDetails.genres[2].name
                        tvMovieGenresDetails.text = genres
                    }
                    movieDetails.genres.size == 2 -> {
                        val genres = movieDetails.genres[0].name + ", " + movieDetails.genres[1].name
                        tvMovieGenresDetails.text = genres
                    }
                    else -> {
                        tvMovieGenresDetails.text = movieDetails.genres[0].name
                    }
                }
            } else {
                // display no genres available
            }

            // Runtime
            val hours = (movieDetails.runtime / 60)
            val minutes = (movieDetails.runtime % 60)
            val hoursAndMinutes: String = hours.toString() + "h " + minutes.toString() + "m"
            tvMovieRuntimeDetails.text = hoursAndMinutes

            // Rating
            rbMovieDetails.rating = ((movieDetails.voteAverage / 10.0) * 5.0).toFloat()

            // Overview
            if(movieDetails.overview != "")
                tvMovieOverview.text = movieDetails.overview
            else {
                tvMovieOverview.text = resources.getString(R.string.txt_no_overview)
            }

            // Available at
            if(movieDetails.watchProviders.results?.br != null) {
                if(movieDetails.watchProviders.results.br.flatrate != null && movieDetails.watchProviders.results.br.flatrate.isNotEmpty()) {
                    when {
                        movieDetails.watchProviders.results.br.flatrate.size == 1 -> {
                            ivMovieProviderOne.visibility = View.VISIBLE
                            val logoPath = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[0].logoPath
                            Glide
                                .with(root)
                                .load(logoPath)
                                .into(ivMovieProviderOne)
                        }
                        movieDetails.watchProviders.results.br.flatrate.size == 2 -> {
                            ivMovieProviderOne.visibility = View.VISIBLE
                            val logoPath = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[0].logoPath
                            Glide
                                .with(root)
                                .load(logoPath)
                                .into(ivMovieProviderOne)

                            ivMovieProviderTwo.visibility = View.VISIBLE
                            val logoPathTwo = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[1].logoPath
                            Glide
                                .with(root)
                                .load(logoPathTwo)
                                .into(ivMovieProviderTwo)
                        }
                        movieDetails.watchProviders.results.br.flatrate.size == 3 -> {
                            ivMovieProviderOne.visibility = View.VISIBLE
                            val logoPath = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[0].logoPath
                            Glide
                                .with(root)
                                .load(logoPath)
                                .into(ivMovieProviderOne)

                            ivMovieProviderTwo.visibility = View.VISIBLE
                            val logoPathTwo = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[1].logoPath
                            Glide
                                .with(root)
                                .load(logoPathTwo)
                                .into(ivMovieProviderTwo)

                            ivMovieProviderThree.visibility = View.VISIBLE
                            val logoPathThree = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[2].logoPath
                            Glide
                                .with(root)
                                .load(logoPathThree)
                                .into(ivMovieProviderThree)
                        }
                        movieDetails.watchProviders.results.br.flatrate.size >= 4 -> {
                            ivMovieProviderOne.visibility = View.VISIBLE
                            val logoPath = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[0].logoPath
                            Glide
                                .with(root)
                                .load(logoPath)
                                .into(ivMovieProviderOne)

                            ivMovieProviderTwo.visibility = View.VISIBLE
                            val logoPathTwo = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[1].logoPath
                            Glide
                                .with(root)
                                .load(logoPathTwo)
                                .into(ivMovieProviderTwo)

                            ivMovieProviderThree.visibility = View.VISIBLE
                            val logoPathThree = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[2].logoPath
                            Glide
                                .with(root)
                                .load(logoPathThree)
                                .into(ivMovieProviderThree)

                            ivMovieProviderFour.visibility = View.VISIBLE
                            val logoPathFour = "https://image.tmdb.org/t/p/original/" + movieDetails.watchProviders.results.br.flatrate[3].logoPath
                            Glide
                                .with(root)
                                .load(logoPathFour)
                                .into(ivMovieProviderFour)
                        }
                    }
                } else {
                    tvMovieNotAvailable.visibility = View.VISIBLE
                }
            } else {
                tvMovieNotAvailable.visibility = View.VISIBLE // no brasil
            }

            // Cast
            rvMovieCast.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            castAdapter.submitList(movieDetails.credits.cast)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}