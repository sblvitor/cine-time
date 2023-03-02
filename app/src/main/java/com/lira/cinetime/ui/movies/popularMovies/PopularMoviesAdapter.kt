package com.lira.cinetime.ui.movies.popularMovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.core.genresIDsToNamesResources
import com.lira.cinetime.data.models.movies.popularMovies.PopularMoviesResult
import com.lira.cinetime.databinding.ItemPopularMovieBinding
import com.lira.cinetime.ui.movies.MoviesFragmentDirections

class PopularMoviesAdapter: PagingDataAdapter<PopularMoviesResult, PopularMoviesAdapter.ViewHolder>(
    DiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder(private val binding: ItemPopularMovieBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopularMoviesResult) {

            val backdropPath: String = "https://image.tmdb.org/t/p/original/" + item.backdropPath

            Glide
                .with(binding.root.context)
                .load(backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .into(binding.ivPopularMovieBackdrop)

            binding.tvPopularMovieTitle.text = item.title

            binding.rbPopularMovie.rating = ((item.voteAverage / 10.0) * 5.0).toFloat()

            if(item.genreIDS.isNotEmpty()) {
                when {
                    item.genreIDS.size >= 3 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[2]))
                        binding.tvPopularMovieGenres.text = genres
                    }
                    item.genreIDS.size == 2 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1]))
                        binding.tvPopularMovieGenres.text = genres
                    }
                    else -> {
                        binding.tvPopularMovieGenres.text = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    }
                }
            } else {
                binding.tvPopularMovieGenres.visibility = View.GONE
            }

            itemView.setOnClickListener {
                val action = MoviesFragmentDirections.actionNavMoviesToNavMovieDetails(item.id)
                it.findNavController().navigate(action)
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<PopularMoviesResult>() {

        override fun areItemsTheSame(oldItem: PopularMoviesResult, newItem: PopularMoviesResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: PopularMoviesResult, newItem: PopularMoviesResult) = oldItem.id == newItem.id

    }

}