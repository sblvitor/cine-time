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
import com.lira.cinetime.data.models.firebase.ParentTv
import com.lira.cinetime.databinding.ItemParentTvBinding

class ParentTvAdapter: ListAdapter<ParentTv, ParentTvAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemParentTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemParentTvBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ParentTv) {
            binding.tvSubtitleTv.text = item.title

            if(item.tvShows.isEmpty()) {
                binding.ibAddTv.visibility = View.VISIBLE
                binding.ibAddTv.setOnClickListener {
                    it.findNavController().navigate(R.id.action_nav_my_list_to_nav_search)
                }
            }

            val adapter = ChildTvAdapter()
            binding.rvTvLists.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvTvLists.adapter = adapter
            adapter.submitList(item.tvShows)
        }

    }

    class DiffCallBack: DiffUtil.ItemCallback<ParentTv>() {
        override fun areItemsTheSame(oldItem: ParentTv, newItem: ParentTv) = oldItem == newItem

        override fun areContentsTheSame(oldItem: ParentTv, newItem: ParentTv) = oldItem.tvShows == newItem.tvShows

    }
}