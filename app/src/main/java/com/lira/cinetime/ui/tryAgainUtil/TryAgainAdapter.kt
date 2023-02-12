package com.lira.cinetime.ui.tryAgainUtil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lira.cinetime.databinding.RetryBinding

class TryAgainAdapter(private val retry: () -> Unit): LoadStateAdapter<TryAgainAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RetryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, retry)
    }

    inner class ViewHolder(private val binding: RetryBinding, retry: () -> Unit): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnLoadMoreRetry.setOnClickListener { retry() }
        }

        fun setData(states: LoadState) {
            binding.apply {
                progressBarRetry.isVisible = states is LoadState.Loading
                tvLoadMore.isVisible = states is LoadState.Error
                btnLoadMoreRetry.isVisible = states is LoadState.Error
            }
        }
    }
}