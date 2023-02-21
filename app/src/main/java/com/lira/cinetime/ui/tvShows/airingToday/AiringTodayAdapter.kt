package com.lira.cinetime.ui.tvShows.airingToday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.core.genresIDsToNamesResources
import com.lira.cinetime.data.models.tvShows.airingToday.AiringTodayTvResult
import com.lira.cinetime.databinding.ItemAiringTodayBinding

class AiringTodayAdapter: PagingDataAdapter<AiringTodayTvResult, AiringTodayAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAiringTodayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemAiringTodayBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AiringTodayTvResult) {
            binding.apply {
                tvAiringTodayTvTitle.text = item.name
                val rating = "Rating: " + item.voteAverage.toString()
                binding.tvAiringTodayRating.text = rating
                if(item.genreIDS.isNotEmpty()) {
                    if (item.genreIDS.size >= 2) {
                        airingTodayTvChipOne.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                        binding.airingTodayTvChipTwo.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[1]))
                    } else {
                        binding.airingTodayTvChipOne.text =
                            itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                        binding.airingTodayTvChipTwo.visibility = View.GONE
                    }
                } else {
                    binding.airingTodayTvChipOne.visibility = View.GONE
                    binding.airingTodayTvChipTwo.visibility = View.GONE
                }

                val posterPath: String = "https://image.tmdb.org/t/p/w342/" + item.posterPath

                Glide
                    .with(binding.root.context)
                    .load(posterPath)
                    .placeholder(R.drawable.film_poster_placeholder)
                    .into(binding.ivAiringTodayTvPoster)
            }
        }

    }

    class DiffCallBack: DiffUtil.ItemCallback<AiringTodayTvResult>() {
        override fun areItemsTheSame(oldItem: AiringTodayTvResult, newItem: AiringTodayTvResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: AiringTodayTvResult, newItem: AiringTodayTvResult) = oldItem.id == newItem.id

    }

}