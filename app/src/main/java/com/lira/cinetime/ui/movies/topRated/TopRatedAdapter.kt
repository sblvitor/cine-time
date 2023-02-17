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
import com.lira.cinetime.data.models.topRated.TopRatedResult
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
            binding.tvTopRatedTitle.text = item.title
            val rating = "Rating: " + item.voteAverage.toString()
            binding.tvRatingTopRated.text = rating
            if(item.genreIDS.isNotEmpty()) {
                if (item.genreIDS.size >= 2) {
                    binding.topRatedChipOne.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    binding.topRatedChipTwo.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[1]))
                } else {
                    binding.topRatedChipOne.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    binding.topRatedChipTwo.visibility = View.GONE
                }
            } else {
                binding.topRatedChipOne.visibility = View.GONE
                binding.topRatedChipTwo.visibility = View.GONE
            }

            val posterPath: String = "https://image.tmdb.org/t/p/w342/" + item.posterPath

            Glide
                .with(binding.root.context)
                .load(posterPath)
                .placeholder(R.drawable.film_poster_placeholder)
                .into(binding.ivTopRatedPoster)

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