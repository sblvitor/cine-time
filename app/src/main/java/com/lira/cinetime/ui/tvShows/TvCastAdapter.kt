package com.lira.cinetime.ui.tvShows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.cinetime.R
import com.lira.cinetime.data.models.tvShows.tvDetails.CastTv
import com.lira.cinetime.databinding.ItemTvCastBinding

class TvCastAdapter: ListAdapter<CastTv, TvCastAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTvCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemTvCastBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CastTv) {
            binding.apply {
                val profilePath: String = "https://image.tmdb.org/t/p/original" + item.profilePath
                if(item.profilePath != null){
                    Glide
                        .with(binding.root.context)
                        .load(profilePath)
                        .placeholder(R.drawable.person_placeholder)
                        .into(ivTvCastProfile)
                } else {
                    ivTvCastProfile.setImageResource(R.drawable.person_placeholder)
                }


                tvTvCastName.text = item.name
                tvTvCharacterName.text = item.roles?.get(0)?.character ?: ""
            }
        }

    }


    class DiffCallBack: DiffUtil.ItemCallback<CastTv>() {
        override fun areItemsTheSame(oldItem: CastTv, newItem: CastTv) = oldItem == newItem

        override fun areContentsTheSame(oldItem: CastTv, newItem: CastTv) = oldItem.id == newItem.id

    }
}