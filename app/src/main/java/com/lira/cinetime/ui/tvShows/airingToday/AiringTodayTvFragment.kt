package com.lira.cinetime.ui.tvShows.airingToday

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
import com.lira.cinetime.databinding.FragmentAiringTodayTvBinding
import com.lira.cinetime.presentation.tvShows.AiringTodayTvViewModel
import com.lira.cinetime.ui.tryAgainUtil.TryAgainAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AiringTodayTvFragment : Fragment() {

    private var _binding: FragmentAiringTodayTvBinding? = null
    private val airingTodayTvViewModel by viewModel<AiringTodayTvViewModel>()
    private val adapter by lazy { AiringTodayAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAiringTodayTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAiringTodayTv.adapter = adapter

        lifecycleScope.launch {
            airingTodayTvViewModel.airingToday.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                adapter.loadStateFlow.collect {
                    val state = it.refresh
                    binding.progressBarAiringTodayTv.isVisible = state is LoadState.Loading
                }
            }
        }

        binding.rvAiringTodayTv.adapter = adapter.withLoadStateFooter(
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
                binding.rvAiringTodayTv.smoothScrollToPosition(0)
                activity?.findViewById<AppBarLayout>(R.id.app_bar_layout)?.setExpanded(true,true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.setOnItemReselectedListener { item ->
            if(item.itemId == R.id.nav_tv_shows){
                binding.rvAiringTodayTv.stopScroll()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}