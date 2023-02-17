package com.lira.cinetime.ui.tvShows.popular

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lira.cinetime.R
import com.lira.cinetime.presentation.tvShows.PopularTvViewModel

class PopularTvFragment : Fragment() {

    companion object {
        fun newInstance() = PopularTvFragment()
    }

    private lateinit var viewModel: PopularTvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PopularTvViewModel::class.java)
        // TODO: Use the ViewModel
    }

}