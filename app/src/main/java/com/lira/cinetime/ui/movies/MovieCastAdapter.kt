package com.lira.cinetime.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.data.models.movies.movieDetails.Cast
import com.lira.cinetime.databinding.ItemCastBinding

class MovieCastAdapter: ListAdapter<Cast, MovieCastAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cast) {
            binding.apply {
                val profilePath: String = "https://image.tmdb.org/t/p/original" + item.profilePath
                if(item.profilePath != null){
                    Glide
                        .with(binding.root.context)
                        .load(profilePath)
                        .placeholder(R.drawable.person_placeholder)
                        .into(ivCastProfile)
                } else {
                    ivCastProfile.setImageResource(R.drawable.person_placeholder)
                }


                tvCastName.text = item.name
                tvCharacterName.text = item.character
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast) = oldItem.id == newItem.id

    }

}