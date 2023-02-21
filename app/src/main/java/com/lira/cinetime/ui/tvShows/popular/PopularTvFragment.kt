package com.lira.cinetime.ui.tvShows.popular

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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lira.cinetime.R
import com.lira.cinetime.databinding.FragmentPopularTvBinding
import com.lira.cinetime.presentation.tvShows.PopularTvViewModel
import com.lira.cinetime.ui.tryAgainUtil.TryAgainAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularTvFragment : Fragment() {

    private var _binding: FragmentPopularTvBinding? = null
    private val popularTvViewModel by viewModel<PopularTvViewModel>()
    private val adapter by lazy { PopularTvAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPopularTv.adapter = adapter

        lifecycleScope.launch {
            popularTvViewModel.popularTv.collectLatest { tvShows ->
                adapter.submitData(tvShows)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                adapter.loadStateFlow.collect {
                    val state = it.refresh
                    binding.progressBarPopularTv.isVisible = state is LoadState.Loading
                }
            }
        }

        binding.rvPopularTv.adapter = adapter.withLoadStateFooter(
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
                binding.rvPopularTv.smoothScrollToPosition(0)
                activity?.findViewById<AppBarLayout>(R.id.app_bar_layout)?.setExpanded(true,true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.setOnItemReselectedListener { item ->
            if(item.itemId == R.id.nav_tv_shows){
                binding.rvPopularTv.stopScroll()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}