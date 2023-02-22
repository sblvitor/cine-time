package com.lira.cinetime.ui.tvShows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lira.cinetime.R
import com.lira.cinetime.databinding.FragmentTvShowBinding

class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tabLayoutTv
        val viewPager2 = binding.viewPagerTv
        val tvShowViewPagerAdapter = TvShowViewPagerAdapter(this)

        viewPager2.adapter = tvShowViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.popular_label)
                1 -> tab.text = getString(R.string.airing_today_label)
                2 -> tab.text = getString(R.string.top_rated_label)
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}