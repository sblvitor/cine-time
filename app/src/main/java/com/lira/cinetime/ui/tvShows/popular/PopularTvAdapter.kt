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

            val backdropPath: String = "https://image.tmdb.org/t/p/original/" + item.backdropPath

            Glide
                .with(binding.root.context)
                .load(backdropPath)
                .placeholder(R.drawable.backdrop_placeholder)
                .into(binding.ivPopularTvBackdrop)

            binding.tvPopularTvTitle.text = item.name

            binding.rbPopularTv.rating = ((item.voteAverage / 10.0) * 5.0).toFloat()

            if(item.genreIDS.isNotEmpty()) {
                when {
                    item.genreIDS.size >= 3 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[2]))
                        binding.tvPopularTvGenres.text = genres
                    }
                    item.genreIDS.size == 2 -> {
                        val genres = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0])) + ", " +
                                itemView.context.getString(genresIDsToNamesResources(item.genreIDS[1]))
                        binding.tvPopularTvGenres.text = genres
                    }
                    else -> {
                        binding.tvPopularTvGenres.text = itemView.context.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    }
                }
            } else {
                binding.tvPopularTvGenres.visibility = View.INVISIBLE
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