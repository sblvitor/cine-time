package com.lira.cinetime.ui.movies

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lira.cinetime.ui.movies.nowPlaying.NowPlayingFragment
import com.lira.cinetime.ui.movies.popularMovies.PopularMoviesFragment
import com.lira.cinetime.ui.movies.topRated.TopRatedFragment

class MoviesViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PopularMoviesFragment()
            1 -> NowPlayingFragment()
            2 -> TopRatedFragment()
            else -> PopularMoviesFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}