package com.lira.cinetime.ui.tvShows.popular

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
import com.lira.cinetime.data.models.tvShows.popularTv.PopularTvResult
import com.lira.cinetime.databinding.ItemPopularTvShowBinding
import com.lira.cinetime.ui.tvShows.TvShowFragmentDirections

class PopularTvAdapter: PagingDataAdapter<PopularTvResult, PopularTvAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPopularTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        //holder.setIsRecyclable(false)
    }

    inner class ViewHolder(private val binding: ItemPopularTvShowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopularTvResult) {
            binding.apply {
                tvPopularTvTitle.text = item.name
                val rating = "Rating: " + item.voteAverage.toString()
                binding.tvTvRating.text = rating
                if(item.genreIDS.isNotEmpty()) {
                    if (item.genreIDS.size >= 2) {
                        popularTvChipOne.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                        binding.popularTvChipTwo.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[1]))
                    } else {
                        binding.popularTvChipOne.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                        binding.popularTvChipTwo.visibility = View.GONE
                    }
                } else {
                    binding.popularTvChipOne.visibility = View.GONE
                    binding.popularTvChipTwo.visibility = View.GONE
                }

                val posterPath: String = "https://image.tmdb.org/t/p/w342/" + item.posterPath

                Glide
                    .with(binding.root.context)
                    .load(posterPath)
                    .placeholder(R.drawable.film_poster_placeholder)
                    .into(binding.ivPopularTvPoster)

            }
            itemView.setOnClickListener {
                val action = TvShowFragmentDirections.actionNavTvShowsToNavTvDetails(item.id)
                it.findNavController().navigate(action)
            }
        }

    }

    class DiffCallBack: DiffUtil.ItemCallback<PopularTvResult>() {
        override fun areItemsTheSame(oldItem: PopularTvResult, newItem: PopularTvResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: PopularTvResult, newItem: PopularTvResult) = oldItem.id == newItem.id
    }

}