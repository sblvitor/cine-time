package com.lira.cinetime.ui.tvShows

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
import com.lira.cinetime.R
import com.lira.cinetime.core.createDialog
import com.lira.cinetime.core.createProgressDialog
import com.lira.cinetime.data.models.tvShows.tvDetails.TvDetailsResponse
import com.lira.cinetime.databinding.FragmentTvDetailsBinding
import com.lira.cinetime.presentation.tvShows.TvDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TvDetailsFragment : Fragment() {

    private var _binding: FragmentTvDetailsBinding? = null
    private val tvDetailsViewModel by viewModel<TvDetailsViewModel>()
    private val dialog by lazy { createProgressDialog() }
    private val castAdapter by lazy { TvCastAdapter() }

    private val args: TvDetailsFragmentArgs by navArgs()

    private var isFavorite: Boolean = false
    private var isInToWatch: Boolean = false

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTvDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvId = args.tvId
        tvDetailsViewModel.getTvDetails(tvId)
        tvDetailsViewModel.checkIfFavorite(tvId)
        tvDetailsViewModel.checkIfInToWatch(tvId)
        binding.rvTvCast.adapter = castAdapter
        collectData()
        setupAppBar()
    }

    private fun setupAppBar() {
        binding.tvDetailsToolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                tvDetailsViewModel.tvDetails.collect {
                    when(it) {
                        TvDetailsViewModel.State.Loading -> {
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                            dialog.show()
                        }
                        is TvDetailsViewModel.State.Error -> {
                            dialog.dismiss()
                            createDialog {
                                setMessage(it.error.message)
                            }.show()
                        }
                        is TvDetailsViewModel.State.Success -> {
                            dialog.dismiss()
                            setupUI(it.tvDetails)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                tvDetailsViewModel.dbOperations.collectLatest {
                    when (it) {
                        TvDetailsViewModel.DBState.LoadingDB -> {
                            // nothing
                        }
                        is TvDetailsViewModel.DBState.AddFavoriteFailure -> {
                            Snackbar
                                .make(binding.root, "${it.error.message}", Snackbar.LENGTH_LONG)
                                .show()
                            binding.tvDetailsToolbar.menu.findItem(R.id.favorite_list_button).icon =
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.favorite_border,
                                    null
                                )
                        }
                        is TvDetailsViewModel.DBState.AddFavoriteSuccess -> {
                            Snackbar
                                .make(
                                    binding.root,
                                    "Adicionado aos favoritos!",
                                    Snackbar.LENGTH_LONG
                                )
                                .show()
                            isFavorite = true
                        }
                        is TvDetailsViewModel.DBState.AddToWatchFailure -> {
                            Snackbar
                                .make(binding.root, "${it.error.message}", Snackbar.LENGTH_LONG)
                                .show()
                            binding.tvDetailsToolbar.menu.findItem(R.id.watch_list_button).icon =
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.playlist_add,
                                    null
                                )
                        }
                        is TvDetailsViewModel.DBState.AddToWatchSuccess -> {
                            Snackbar
                                .make(
                                    binding.root,
                                    "Adicionado ?? lista \"Para assistir\"!",
                                    Snackbar.LENGTH_LONG
                                )
                                .show()
                            isInToWatch = true
                        }
                        is TvDetailsViewModel.DBState.IsFavorite -> {
                            if (it.favorite) {
                                isFavorite = true
                                binding.tvDetailsToolbar.menu.findItem(R.id.favorite_list_button).icon =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.favorite,
                                        null
                                    )
                            }
                        }
                        is TvDetailsViewModel.DBState.IsInToWatch -> {
                            if (it.inToWatch) {
                                isInToWatch = true
                                binding.tvDetailsToolbar.menu.findItem(R.id.watch_list_button).icon =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.playlist_remove,
                                        null
                                    )
                            }
                        }
                        is TvDetailsViewModel.DBState.ErrorDB -> {
                            createDialog {
                                setMessage(it.error.message)
                            }.show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUI(tvDetails: TvDetailsResponse) {
        binding.apply {
            // Menu
            tvDetailsToolbar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.favorite_list_button -> {
                        if(!isFavorite) {
                            menuItem.icon = ResourcesCompat.getDrawable(resources, R.drawable.favorite, null)
                            tvDetailsViewModel.addToFavorites(
                                tvId = tvDetails.id,
                                name = tvDetails.name,
                                posterPath = tvDetails.posterPath
                            )
                        } else {
                            menuItem.icon = ResourcesCompat.getDrawable(resources, R.drawable.favorite_border, null)
                            tvDetailsViewModel.deleteFavoriteTv(tvDetails.id)
                            isFavorite = false
                        }
                        true
                    }
                    R.id.watch_list_button -> {
                        if(!isInToWatch) {
                            menuItem.icon = ResourcesCompat.getDrawable(resources, R.drawable.playlist_remove, null)
                            tvDetailsViewModel.addToWatchList(
                                tvId = tvDetails.id,
                                name = tvDetails.name,
                                posterPath = tvDetails.posterPath
                            )
                        } else {
                            menuItem.icon = ResourcesCompat.getDrawable(resources, R.drawable.playlist_add, null)
                            tvDetailsViewModel.deleteTvInToWatch(tvDetails.id)
                            isInToWatch = false
                        }
                        true
                    }
                    else -> false
                }
            }

            // Poster img
            val backdropPath: String = "https://image.tmdb.org/t/p/original/" + tvDetails.backdropPath
            Glide
                .with(root)
                .load(backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .into(ivTvPosterDetails)

            // Title
            tvTvTitleDetails.text = tvDetails.name

            // Genres
            if(tvDetails.genres.isNotEmpty()) {
                when {
                    tvDetails.genres.size >= 4 -> {
                        val genres = tvDetails.genres[0].name + ", " + tvDetails.genres[1].name + ", " + tvDetails.genres[2].name + ", " + tvDetails.genres[3].name
                        tvTvGenresDetails.text = genres
                    }
                    tvDetails.genres.size == 3 -> {
                        val genres = tvDetails.genres[0].name + ", " + tvDetails.genres[1].name + ", " + tvDetails.genres[2].name
                        tvTvGenresDetails.text = genres
                    }
                    tvDetails.genres.size == 2 -> {
                        val genres = tvDetails.genres[0].name + ", " + tvDetails.genres[1].name
                        tvTvGenresDetails.text = genres
                    }
                    else -> tvTvGenresDetails.text = tvDetails.genres[0].name
                }
            } else {
                // display no genres available
            }

            // Rating
            rbTvDetails.rating = ((tvDetails.voteAverage / 10.0) * 5.0).toFloat()

            // Number of seasons and episodes
            if(tvDetails.numberOfSeasons == 1){
                val seasons = tvDetails.numberOfSeasons.toString() + " " + getString(R.string.season)
                tvNumberOfSeasons.text = seasons
            } else {
                val seasons = tvDetails.numberOfSeasons.toString() + " " + getString(R.string.seasons)
                tvNumberOfSeasons.text = seasons
            }
            val episodes = tvDetails.numberOfEpisodes.toString()+ " " + getString(R.string.episodes)
            tvNumberOfEpisodes.text = episodes

            // Overview
            if(tvDetails.overview != "") {
                tvTvOverview.text = tvDetails.overview
            } else {
                tvTvOverview.text = getString(R.string.txt_no_overview)
            }

            // Last Air Date and Next Air Date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                if(tvDetails.lastAirDate != null){
                    val lastAirDate =  LocalDate.parse(tvDetails.lastAirDate)
                    val lastAirDateText = getString(R.string.last_air_date) + " " + lastAirDate.format(formatter)
                    tvLastAirDate.text = lastAirDateText
                }

                if(tvDetails.nextEpisodeToAir != null){
                    val nextAirDate = LocalDate.parse(tvDetails.nextEpisodeToAir.airDate)
                    val nextAirDateText = getString(R.string.next_air_date) + " " + nextAirDate.format(formatter)
                    tvNextAirDate.text = nextAirDateText
                } else {
                    tvNextAirDate.visibility = View.GONE
                }

            } else {
                tvLastAirDate.visibility = View.GONE
                tvNextAirDate.visibility = View.GONE
            }

            // Available At
            if(tvDetails.watchProviders.results?.br != null) {
                if(tvDetails.watchProviders.results.br.flatRate != null && tvDetails.watchProviders.results.br.flatRate.isNotEmpty()) {
                    when {
                        tvDetails.watchProviders.results.br.flatRate.size == 1 -> {
                            ivTvProviderOne.visibility = View.VISIBLE
                            val logoPath = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[0].logoPath
                            Glide
                                .with(root)
                                .load(logoPath)
                                .into(ivTvProviderOne)
                        }
                        tvDetails.watchProviders.results.br.flatRate.size == 2 -> {
                            ivTvProviderOne.visibility = View.VISIBLE
                            val logoPath = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[0].logoPath
                            Glide
                                .with(root)
                                .load(logoPath)
                                .into(ivTvProviderOne)

                            ivTvProviderTwo.visibility = View.VISIBLE
                            val logoPathTwo = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[1].logoPath
                            Glide
                                .with(root)
                                .load(logoPathTwo)
                                .into(ivTvProviderTwo)
                        }
                        tvDetails.watchProviders.results.br.flatRate.size == 3 -> {
                            ivTvProviderOne.visibility = View.VISIBLE
                            val logoPath = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[0].logoPath
                            Glide
                                .with(root)
                                .load(logoPath)
                                .into(ivTvProviderOne)

                            ivTvProviderTwo.visibility = View.VISIBLE
                            val logoPathTwo = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[1].logoPath
                            Glide
                                .with(root)
                                .load(logoPathTwo)
                                .into(ivTvProviderTwo)

                            ivTvProviderThree.visibility = View.VISIBLE
                            val logoPathThree = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[2].logoPath
                            Glide
                                .with(root)
                                .load(logoPathThree)
                                .into(ivTvProviderThree)
                        }
                        tvDetails.watchProviders.results.br.flatRate.size >= 4 -> {
                            ivTvProviderOne.visibility = View.VISIBLE
                            val logoPath = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[0].logoPath
                            Glide
                                .with(root)
                                .load(logoPath)
                                .into(ivTvProviderOne)

                            ivTvProviderTwo.visibility = View.VISIBLE
                            val logoPathTwo = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[1].logoPath
                            Glide
                                .with(root)
                                .load(logoPathTwo)
                                .into(ivTvProviderTwo)

                            ivTvProviderThree.visibility = View.VISIBLE
                            val logoPathThree = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[2].logoPath
                            Glide
                                .with(root)
                                .load(logoPathThree)
                                .into(ivTvProviderThree)

                            ivTvProviderFour.visibility = View.VISIBLE
                            val logoPathFour = "https://image.tmdb.org/t/p/original/" + tvDetails.watchProviders.results.br.flatRate[3].logoPath
                            Glide
                                .with(root)
                                .load(logoPathFour)
                                .into(ivTvProviderFour)
                        }
                    }
                } else {
                    tvTvNotAvailable.visibility = View.VISIBLE
                }
            } else {
                tvTvNotAvailable.visibility = View.VISIBLE // no Brasil
            }

            // Cast
            rvTvCast.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            castAdapter.submitList(tvDetails.aggregateCredits.cast)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}