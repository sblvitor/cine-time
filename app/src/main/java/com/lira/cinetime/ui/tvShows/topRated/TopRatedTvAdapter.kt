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
            val backdropPath: String = "https://image.tmdb.org/t/p/original/" + item.backdropPath

            Glide
                .with(binding.root.context)
                .load(backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .into(binding.ivTopRatedTvBackdrop)

            binding.tvTopRatedTvTitle.text = item.name

            binding.rbTopRatedTv.rating = ((item.voteAverage / 10.0) * 5.0).toFloat()

            if(item.genreIDS.isNotEmpty()) {
                when {
                    item.genreIDS.size >= 3 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[2]))
                        binding.tvToRatedTvGenres.text = genres
                    }
                    item.genreIDS.size == 2 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1]))
                        binding.tvToRatedTvGenres.text = genres
                    }
                    else -> {
                        binding.tvToRatedTvGenres.text = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    }
                }
            } else {
                binding.tvToRatedTvGenres.visibility = View.INVISIBLE
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