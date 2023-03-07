package com.lira.cinetime.ui.myList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.lira.cinetime.R
import com.lira.cinetime.core.createProgressDialog
import com.lira.cinetime.data.models.firebase.ParentMovie
import com.lira.cinetime.data.models.firebase.ParentTv
import com.lira.cinetime.databinding.FragmentMyListBinding
import com.lira.cinetime.presentation.myList.MyListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyListFragment : Fragment() {

    private var _binding: FragmentMyListBinding? = null
    private val myListViewModel by viewModel<MyListViewModel>()
    private val dialog by lazy { createProgressDialog() }

    private val parentMoviesAdapter by lazy { ParentMoviesAdapter() }
    private val parentTvAdapter by lazy { ParentTvAdapter() }
    private val movieHeaderAdapter by lazy { MovieHeaderAdapter() }
    private val tvHeaderAdapter by lazy { TvHeaderAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val concatAdapter = ConcatAdapter(movieHeaderAdapter, parentMoviesAdapter, tvHeaderAdapter, parentTvAdapter)
        binding.rvLists.adapter = concatAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myListViewModel.allData.collectLatest {
                    when(it) {
                        MyListViewModel.State.Loading -> {
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                            dialog.show()
                        }
                        is MyListViewModel.State.Success -> {
                            dialog.dismiss()
                            parentMoviesAdapter.submitList(
                                listOf(
                                    ParentMovie(getString(R.string.to_watch_movies_label), it.result.toWatchMovies),
                                    ParentMovie(getString(R.string.favorite_movies_label), it.result.favMovies)
                                )
                            )
                            parentTvAdapter.submitList(
                                listOf(
                                    ParentTv(getString(R.string.to_watch_tv_shows_label), it.result.toWatchTvShows),
                                    ParentTv(getString(R.string.favorite_tv_label), it.result.favTvShows)
                                )
                            )
                        }
                    }
                    //result.toWatchTvShows.tvShow?.let { toWatchTv -> tvList.add(ParentTv(getString(R.string.to_watch_tv_shows_label), toWatchTv)) }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}