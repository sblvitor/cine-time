package com.lira.cinetime.ui.movies.topRated

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
import com.lira.cinetime.databinding.FragmentTopRatedBinding
import com.lira.cinetime.presentation.movies.TopRatedViewModel
import com.lira.cinetime.ui.tryAgainUtil.TryAgainAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopRatedFragment : Fragment() {

    private var _binding: FragmentTopRatedBinding? = null
    private val topRatedViewModel by viewModel<TopRatedViewModel>()
    private val adapter by lazy { TopRatedAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTopRated.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            topRatedViewModel.getTopRatedMovies().collectLatest { topRatedMovies ->
                adapter.submitData(topRatedMovies)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                adapter.loadStateFlow.collect {
                    val state = it.refresh
                    binding.progressBarTopRated.isVisible = state is LoadState.Loading
                }
            }
        }

        binding.rvTopRated.adapter = adapter.withLoadStateFooter(
            TryAgainAdapter {
                adapter.retry()
            }
        )

    }

    override fun onResume() {
        super.onResume()
        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.setOnItemReselectedListener { item ->
            if(item.itemId == R.id.nav_movies){
                binding.rvTopRated.smoothScrollToPosition(0)
                activity?.findViewById<AppBarLayout>(R.id.app_bar_layout)?.setExpanded(true,true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.setOnItemReselectedListener { item ->
            if(item.itemId == R.id.nav_movies){
                binding.rvTopRated.stopScroll()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}