package com.lira.cinetime.ui.tvShows.topRated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lira.cinetime.R
import com.lira.cinetime.databinding.FragmentTopRatedTvBinding
import com.lira.cinetime.presentation.tvShows.TopRatedTvViewModel
import com.lira.cinetime.ui.tryAgainUtil.TryAgainAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopRatedTvFragment : Fragment() {

    private var _binding: FragmentTopRatedTvBinding? = null
    private val topRatedTvViewModel by viewModel<TopRatedTvViewModel>()
    private val adapter by lazy { TopRatedTvAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTopRatedTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTopRatedTv.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topRatedTvViewModel.topRatedTv.collectLatest { topRatedTv ->
                    adapter.submitData(topRatedTv)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                adapter.loadStateFlow.collect {
                    val state = it.refresh
                    binding.progressBarTopRatedTv.isVisible = state is LoadState.Loading
                }
            }
        }

        binding.rvTopRatedTv.adapter = adapter.withLoadStateFooter(
            TryAgainAdapter {
                adapter.retry()
            }
        )

    }

    override fun onResume() {
        super.onResume()
        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.setOnItemReselectedListener { item ->
            if(item.itemId == R.id.nav_tv_shows){
                binding.rvTopRatedTv.smoothScrollToPosition(0)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.setOnItemReselectedListener { item ->
            if(item.itemId == R.id.nav_tv_shows){
                binding.rvTopRatedTv.stopScroll()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}