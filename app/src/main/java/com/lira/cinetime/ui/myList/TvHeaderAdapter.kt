package com.lira.cinetime.ui.myList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lira.cinetime.R
import com.lira.cinetime.databinding.HeaderTvBinding

class TvHeaderAdapter: RecyclerView.Adapter<TvHeaderAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: HeaderTvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvTvHeaderTitle.text = binding.root.context.getString(R.string.tv_shows_label)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HeaderTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

}