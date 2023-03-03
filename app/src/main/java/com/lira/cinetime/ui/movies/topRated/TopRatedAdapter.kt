package com.lira.cinetime.ui.movies.topRated

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
import com.lira.cinetime.data.models.movies.topRated.TopRatedResult
import com.lira.cinetime.databinding.ItemTopRatedBinding
import com.lira.cinetime.ui.movies.MoviesFragmentDirections

class TopRatedAdapter: PagingDataAdapter<TopRatedResult, TopRatedAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }


    inner class ViewHolder(private val binding: ItemTopRatedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TopRatedResult) {

            val backdropPath: String = "https://image.tmdb.org/t/p/original/" + item.backdropPath

            Glide
                .with(binding.root.context)
                .load(backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .into(binding.ivTopRatedMovieBackdrop)

            binding.tvTopRatedMovieTitle.text = item.title

            binding.rbTopRatedMovie.rating = ((item.voteAverage / 10.0) * 5.0).toFloat()

            if(item.genreIDS.isNotEmpty()) {
                when {
                    item.genreIDS.size >= 3 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[2]))
                        binding.tvTopRatedMovieGenres.text = genres
                    }
                    item.genreIDS.size == 2 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1]))
                        binding.tvTopRatedMovieGenres.text = genres
                    }
                    else -> {
                        binding.tvTopRatedMovieGenres.text = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    }
                }
            } else {
                binding.tvTopRatedMovieGenres.visibility = View.GONE
            }

            itemView.setOnClickListener {
                val action = MoviesFragmentDirections.actionNavMoviesToNavMovieDetails(item.id)
                it.findNavController().navigate(action)
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<TopRatedResult>() {

        override fun areItemsTheSame(oldItem: TopRatedResult, newItem: TopRatedResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: TopRatedResult, newItem: TopRatedResult) = oldItem.id == newItem.id
    }

}