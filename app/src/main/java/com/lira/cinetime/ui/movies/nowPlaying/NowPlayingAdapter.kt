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
import com.lira.cinetime.data.models.nowPlaying.NowPlayingResult
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
            binding.tvNowPlayingMovieTitle.text = item.title
            val rating = "Rating: " + item.voteAverage.toString()
            binding.nowPlayingTvRating.text = rating
            if(item.genreIDS.isNotEmpty()) {
                if (item.genreIDS.size >= 2) {
                    binding.nowPlayingMovieChipOne.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    binding.nowPlayingMovieChipTwo.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[1]))
                } else {
                    binding.nowPlayingMovieChipOne.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    binding.nowPlayingMovieChipTwo.visibility = View.GONE
                }
            } else {
                binding.nowPlayingMovieChipOne.visibility = View.GONE
                binding.nowPlayingMovieChipTwo.visibility = View.GONE
            }

            val posterPath: String = "https://image.tmdb.org/t/p/w342/" + item.posterPath

            Glide
                .with(binding.root.context)
                .load(posterPath)
                .placeholder(R.drawable.film_poster_placeholder)
                .into(binding.ivNowPlayingMoviePoster)

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