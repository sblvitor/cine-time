package com.lira.cinetime.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.lira.cinetime.R
import com.lira.cinetime.databinding.FragmentMoviesBinding
import com.lira.cinetime.presentation.movies.MoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val moviesViewModel by viewModel<MoviesViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tabLayoutMovies
        val viewPager2 = binding.viewPagerMovies
        val moviesViewPagerAdapter = MoviesViewPagerAdapter(this)

        viewPager2.adapter = moviesViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.popular_label)
                1 -> tab.text = getString(R.string.now_playing_label)
                2 -> tab.text = getString(R.string.top_rated_label)
            }
        }.attach()

        //Login
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesViewModel.isConnected.collectLatest {
                    if(it == null){
                        val navController = findNavController()
                        navController.navigate(R.id.action_nav_movies_to_navigation_login_flow)
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}