package com.lira.cinetime.ui.popularMovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.core.genresIDsToNamesResources
import com.lira.cinetime.data.models.PopularMoviesResult
import com.lira.cinetime.databinding.ItemPopularMovieBinding

class PopularMoviesAdapter: PagingDataAdapter<PopularMoviesResult, PopularMoviesAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder(private val binding: ItemPopularMovieBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopularMoviesResult) {
            binding.tvPopularMovieTitle.text = item.title
            val rating = "Rating: " + item.voteAverage.toString()
            binding.tvRating.text = rating
            if(item.genreIDS.isNotEmpty()) {
                if (item.genreIDS.size >= 2) {
                    binding.popularMovieChipOne.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    binding.popularMovieChipTwo.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[1]))
                } else {
                    binding.popularMovieChipOne.text =
                        itemView.context.resources.getString(genresIDsToNamesResources(item.genreIDS[0]))
                    binding.popularMovieChipTwo.visibility = View.GONE
                }
            } else {
                binding.popularMovieChipOne.visibility = View.GONE
                binding.popularMovieChipTwo.visibility = View.GONE
            }

            val posterPath: String = "https://image.tmdb.org/t/p/w342/" + item.posterPath

            Glide
                .with(binding.root.context)
                .load(posterPath)
                .into(binding.ivPopularMoviePoster)
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<PopularMoviesResult>() {

        override fun areItemsTheSame(oldItem: PopularMoviesResult, newItem: PopularMoviesResult) = oldItem == newItem

        override fun areContentsTheSame(oldItem: PopularMoviesResult, newItem: PopularMoviesResult) = oldItem.id == newItem.id

    }

}