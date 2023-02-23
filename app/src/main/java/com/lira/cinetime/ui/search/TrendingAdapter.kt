package com.lira.cinetime.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.data.models.search.trending.TrendingResult
import com.lira.cinetime.databinding.ItemTrendingBinding

class TrendingAdapter: PagingDataAdapter<TrendingResult, TrendingAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemTrendingBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrendingResult) {
            val posterPath: String = "https://image.tmdb.org/t/p/w342" + item.posterPath
            if(item.posterPath != null) {
                Glide
                    .with(binding.root.context)
                    .load(posterPath)
                    .placeholder(R.drawable.film_poster_placeholder)
                    .into(binding.ivPosterTrending)
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<TrendingResult>() {

        override fun areItemsTheSame(oldItem: TrendingResult, newItem: TrendingResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: TrendingResult, newItem: TrendingResult) = oldItem.id == newItem.id

    }
}