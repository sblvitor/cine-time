package com.lira.cinetime.ui.tvShows

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lira.cinetime.R
import com.lira.cinetime.presentation.tvShows.TvShowViewModel

class TvShowFragment : Fragment() {

    companion object {
        fun newInstance() = TvShowFragment()
    }

    private lateinit var viewModel: TvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}