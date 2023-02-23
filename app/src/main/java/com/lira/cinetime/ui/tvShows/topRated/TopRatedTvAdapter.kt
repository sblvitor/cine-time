package com.lira.cinetime.ui.tvShows.topRated

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
import com.lira.cinetime.data.models.tvShows.topRated.TopRatedTvResult
import com.lira.cinetime.databinding.ItemTopRatedTvBinding
import com.lira.cinetime.ui.tvShows.TvShowFragmentDirections

class TopRatedTvAdapter: PagingDataAdapter<TopRatedTvResult, TopRatedTvAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTopRatedTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    inner class ViewHolder(private val binding: ItemTopRatedTvBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TopRatedTvResult) {
            binding.apply {
                tvTopRatedTvTitle.text = item.name
                val rating = "Rating: " + item.voteAverage.toString()
                binding.tvTopRatedRating.text = rating
                if(item.genreIDS.isNotEmpty()) {
                    if (item.genreIDS.size >= 2) {
                        topRatedTvChipOne.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                        binding.topRatedTvChipTwo.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[1]))
                    } else {
                        binding.topRatedTvChipOne.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                        binding.topRatedTvChipTwo.visibility = View.GONE
                    }
                } else {
                    binding.topRatedTvChipOne.visibility = View.GONE
                    binding.topRatedTvChipTwo.visibility = View.GONE
                }

                val posterPath: String = "https://image.tmdb.org/t/p/w342/" + item.posterPath

                Glide
                    .with(binding.root.context)
                    .load(posterPath)
                    .placeholder(R.drawable.film_poster_placeholder)
                    .into(binding.ivTopRatedTvPoster)
            }
            itemView.setOnClickListener {
                val action = TvShowFragmentDirections.actionNavTvShowsToNavTvDetails(item.id)
                it.findNavController().navigate(action)
            }
        }

    }

    class DiffCallBack: DiffUtil.ItemCallback<TopRatedTvResult>() {
        override fun areItemsTheSame(oldItem: TopRatedTvResult, newItem: TopRatedTvResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: TopRatedTvResult, newItem: TopRatedTvResult) = oldItem.id == newItem.id

    }

}