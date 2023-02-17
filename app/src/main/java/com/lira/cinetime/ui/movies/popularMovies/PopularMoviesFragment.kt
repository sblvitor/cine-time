package com.lira.cinetime.ui.movies.popularMovies

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
import com.lira.cinetime.databinding.FragmentPopularMoviesBinding
import com.lira.cinetime.presentation.movies.PopularMoviesViewModel
import com.lira.cinetime.ui.tryAgainUtil.TryAgainAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val popularMoviesViewModel by viewModel<PopularMoviesViewModel>()
    private val adapter by lazy { PopularMoviesAdapter() }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPopularMovies.adapter = adapter

        //Movies
        lifecycleScope.launch {
            popularMoviesViewModel.popularMovies.collectLatest { movies ->
                adapter.submitData(movies)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                adapter.loadStateFlow.collect {
                    val state = it.refresh
                    binding.progressBarPopularMovies.isVisible = state is LoadState.Loading
                }
            }
        }

        binding.rvPopularMovies.adapter = adapter.withLoadStateFooter(
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
                binding.rvPopularMovies.smoothScrollToPosition(0)
                activity?.findViewById<AppBarLayout>(R.id.app_bar_layout)?.setExpanded(true,true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.setOnItemReselectedListener { item ->
            if(item.itemId == R.id.nav_movies){
                binding.rvPopularMovies.stopScroll()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}