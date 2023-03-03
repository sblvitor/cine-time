package com.lira.cinetime.ui.movies.nowPlaying

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
import com.lira.cinetime.data.models.movies.nowPlaying.NowPlayingResult
import com.lira.cinetime.databinding.ItemNowPlayingMovieBinding
import com.lira.cinetime.ui.movies.MoviesFragmentDirections

class NowPlayingAdapter: PagingDataAdapter<NowPlayingResult, NowPlayingAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNowPlayingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder(private val binding: ItemNowPlayingMovieBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NowPlayingResult) {

            val backdropPath: String = "https://image.tmdb.org/t/p/original/" + item.backdropPath

            Glide
                .with(binding.root.context)
                .load(backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .into(binding.ivNowPlayingMovieBackdrop)

            binding.tvNowPlayingMovieTitle.text = item.title

            binding.rbNowPlayingMovie.rating = ((item.voteAverage / 10.0) * 5.0).toFloat()

            if(item.genreIDS.isNotEmpty()) {
                when {
                    item.genreIDS.size >= 3 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[2]))
                        binding.tvNowPlayingMovieGenres.text = genres
                    }
                    item.genreIDS.size == 2 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1]))
                        binding.tvNowPlayingMovieGenres.text = genres
                    }
                    else -> {
                        binding.tvNowPlayingMovieGenres.text = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    }
                }
            } else {
                binding.tvNowPlayingMovieGenres.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                val action = MoviesFragmentDirections.actionNavMoviesToNavMovieDetails(item.id)
                it.findNavController().navigate(action)
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<NowPlayingResult>() {

        override fun areItemsTheSame(oldItem: NowPlayingResult, newItem: NowPlayingResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: NowPlayingResult, newItem: NowPlayingResult) = oldItem.id == newItem.id

    }

}