package com.lira.cinetime.ui.myList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.databinding.ItemChildMovieBinding

class ChildMoviesAdapter: ListAdapter<Movie, ChildMoviesAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChildMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemChildMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            val posterPath: String = "https://image.tmdb.org/t/p/w342" + item.posterPath
            if(item.posterPath != null) {
                Glide
                    .with(binding.root.context)
                    .load(posterPath)
                    .placeholder(R.drawable.film_poster_placeholder)
                    .into(binding.ivChildPosterMovie)
            } else {
                binding.ivChildPosterMovie.setImageResource(R.drawable.film_poster_placeholder)
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem.movieId == newItem.movieId

    }

}