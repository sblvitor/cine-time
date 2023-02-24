package com.lira.cinetime.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.data.models.search.multiSearch.MultiSearchResult
import com.lira.cinetime.databinding.ItemSearchResultBinding

class SearchAdapter: PagingDataAdapter<MultiSearchResult, SearchAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MultiSearchResult) {
            when(item.mediaType) {
                "person" -> {
                    if(item.profilePath != null) {
                        val profilePath: String = "https://image.tmdb.org/t/p/w342" + item.profilePath
                        Glide
                            .with(binding.root.context)
                            .load(profilePath)
                            .placeholder(R.drawable.person_placeholder)
                            .into(binding.ivPosterSearch)
                    } else {
                        binding.ivPosterSearch.setImageResource(R.drawable.person_placeholder)
                    }
                }
                else -> {
                    if(item.posterPath != null) {
                        val posterPath: String = "https://image.tmdb.org/t/p/w342" + item.posterPath
                        Glide
                            .with(binding.root.context)
                            .load(posterPath)
                            .placeholder(R.drawable.film_poster_placeholder)
                            .into(binding.ivPosterSearch)
                    } else {
                        binding.ivPosterSearch.setImageResource(R.drawable.film_poster_placeholder)
                    }
                }
            }

            itemView.setOnClickListener {
                when(item.mediaType) {
                    "movie" -> {
                        val action = SearchFragmentDirections.actionNavSearchToNavMovieDetails(item.id)
                        it.findNavController().navigate(action)
                    }
                    "tv" -> {
                        val action = SearchFragmentDirections.actionNavSearchToNavTvDetails(item.id)
                        it.findNavController().navigate(action)
                    }
                }
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<MultiSearchResult>() {

        override fun areItemsTheSame(oldItem: MultiSearchResult, newItem: MultiSearchResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: MultiSearchResult, newItem: MultiSearchResult) = oldItem.id == newItem.id

    }
}