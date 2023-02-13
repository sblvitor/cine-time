package com.lira.cinetime.ui.movies

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lira.cinetime.ui.movies.nowPlaying.NowPlayingFragment
import com.lira.cinetime.ui.movies.popularMovies.PopularMoviesFragment

class MoviesViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PopularMoviesFragment()
            1 -> NowPlayingFragment()
            else -> PopularMoviesFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}