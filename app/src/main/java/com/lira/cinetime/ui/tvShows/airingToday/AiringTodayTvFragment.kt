package com.lira.cinetime.ui.tvShows.airingToday

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lira.cinetime.R
import com.lira.cinetime.presentation.tvShows.AiringTodayTvViewModel

class AiringTodayTvFragment : Fragment() {

    companion object {
        fun newInstance() = AiringTodayTvFragment()
    }

    private lateinit var viewModel: AiringTodayTvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_airing_today_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AiringTodayTvViewModel::class.java)
        // TODO: Use the ViewModel
    }

}