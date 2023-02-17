package com.lira.cinetime.ui.tvShows.topRated

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lira.cinetime.R
import com.lira.cinetime.presentation.tvShows.TopRatedTvViewModel

class TopRatedTvFragment : Fragment() {

    companion object {
        fun newInstance() = TopRatedTvFragment()
    }

    private lateinit var viewModel: TopRatedTvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_rated_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopRatedTvViewModel::class.java)
        // TODO: Use the ViewModel
    }

}