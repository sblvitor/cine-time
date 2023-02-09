package com.lira.cinetime.ui.popularMovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
            val rating = "Rating: " + item.voteAverage
            binding.tvRating.text = rating
            if(item.genreIDS.isNotEmpty()){
                binding.tvPopularMoviesGenres.text = item.genreIDS[0].toString()
            } else {
                binding.tvPopularMoviesGenres.text = "Gêrenos nao disponíveis"
            }


            val posterPath: String = "https://image.tmdb.org/t/p/w500/" + item.posterPath

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