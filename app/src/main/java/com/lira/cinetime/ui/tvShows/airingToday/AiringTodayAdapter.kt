package com.lira.cinetime.ui.tvShows.airingToday

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
import com.lira.cinetime.data.models.tvShows.airingToday.AiringTodayTvResult
import com.lira.cinetime.databinding.ItemAiringTodayBinding
import com.lira.cinetime.ui.tvShows.TvShowFragmentDirections

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
            val backdropPath: String = "https://image.tmdb.org/t/p/original/" + item.backdropPath

            Glide
                .with(binding.root.context)
                .load(backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .into(binding.ivAiringTodayTvBackdrop)

            binding.tvAiringTodayTvTitle.text = item.name

            binding.rbAiringTodayTv.rating = ((item.voteAverage / 10.0) * 5.0).toFloat()

            if(item.genreIDS.isNotEmpty()) {
                when {
                    item.genreIDS.size >= 3 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[2]))
                        binding.tvAiringTodayTvGenres.text = genres
                    }
                    item.genreIDS.size == 2 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1]))
                        binding.tvAiringTodayTvGenres.text = genres
                    }
                    else -> {
                        binding.tvAiringTodayTvGenres.text = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    }
                }
            } else {
                binding.tvAiringTodayTvGenres.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                val action = TvShowFragmentDirections.actionNavTvShowsToNavTvDetails(item.id)
                it.findNavController().navigate(action)
            }
        }

    }

    class DiffCallBack: DiffUtil.ItemCallback<AiringTodayTvResult>() {
        override fun areItemsTheSame(oldItem: AiringTodayTvResult, newItem: AiringTodayTvResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: AiringTodayTvResult, newItem: AiringTodayTvResult) = oldItem.id == newItem.id

    }

}