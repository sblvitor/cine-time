package com.lira.cinetime.ui.myList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lira.cinetime.R
import com.lira.cinetime.databinding.HeaderMoviesBinding

class MovieHeaderAdapter: RecyclerView.Adapter<MovieHeaderAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: HeaderMoviesBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.tvMovieHeaderTitle.text = binding.root.context.getString(R.string.movies_label)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HeaderMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

}