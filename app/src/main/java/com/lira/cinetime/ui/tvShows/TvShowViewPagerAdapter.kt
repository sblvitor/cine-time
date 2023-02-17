package com.lira.cinetime.ui.tvShows

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lira.cinetime.ui.tvShows.airingToday.AiringTodayTvFragment
import com.lira.cinetime.ui.tvShows.popular.PopularTvFragment
import com.lira.cinetime.ui.tvShows.topRated.TopRatedTvFragment

class TvShowViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PopularTvFragment()
            1 -> AiringTodayTvFragment()
            2 -> TopRatedTvFragment()
            else -> PopularTvFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}