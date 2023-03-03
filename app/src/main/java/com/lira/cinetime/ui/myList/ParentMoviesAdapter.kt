package com.lira.cinetime.ui.myList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lira.cinetime.R
import com.lira.cinetime.data.models.firebase.ParentMovie
import com.lira.cinetime.databinding.ItemParentMoviesBinding

class ParentMoviesAdapter: ListAdapter<ParentMovie, ParentMoviesAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemParentMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemParentMoviesBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ParentMovie) {
            binding.tvSubtitleMovies.text = item.title

            if(item.movies.isEmpty()) {
                binding.ibAddMovies.visibility = View.VISIBLE
                binding.ibAddMovies.setOnClickListener {
                    it.findNavController().navigate(R.id.action_nav_my_list_to_nav_search)
                }
            }

            val adapter = ChildMoviesAdapter()
            binding.rvMovieLists.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvMovieLists.adapter = adapter
            adapter.submitList(item.movies)
        }

    }

    class DiffCallBack: DiffUtil.ItemCallback<ParentMovie>() {
        override fun areItemsTheSame(oldItem: ParentMovie, newItem: ParentMovie) = oldItem == newItem

        override fun areContentsTheSame(oldItem: ParentMovie, newItem: ParentMovie) = oldItem.movies == newItem.movies
    }

}