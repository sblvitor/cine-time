package com.lira.cinetime.ui.myList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.data.models.firebase.TvShow
import com.lira.cinetime.databinding.ItemChildTvBinding

class ChildTvAdapter: ListAdapter<TvShow, ChildTvAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChildTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemChildTvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvShow) {
            val posterPath: String = "https://image.tmdb.org/t/p/w342" + item.posterPath
            if(item.posterPath != null) {
                Glide
                    .with(binding.root.context)
                    .load(posterPath)
                    .placeholder(R.drawable.film_poster_placeholder)
                    .into(binding.ivChildTvPoster)
            } else {
                binding.ivChildTvPoster.setImageResource(R.drawable.film_poster_placeholder)
            }
            itemView.setOnClickListener {
                val action = item.tvId?.let { id ->
                    MyListFragmentDirections.actionNavMyListToNavTvDetails(id)
                }
                action?.let { it1 -> it.findNavController().navigate(it1) }
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) = oldItem == newItem

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) = oldItem.tvId == newItem.tvId

    }

}