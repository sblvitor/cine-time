package com.lira.cinetime.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.lira.cinetime.databinding.FragmentSearchBinding
import com.lira.cinetime.presentation.search.SearchViewModel
import com.lira.cinetime.ui.tryAgainUtil.TryAgainAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val searchViewModel by viewModel<SearchViewModel>()
    private val trendingAdapter by lazy { TrendingAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.animationView.repeatCount = 2
        //binding.searchView.setupWithSearchBar(binding.searchBar)
        /*binding.searchView
            .editText
            .setOnEditorActionListener { textView, i, keyEvent ->
                binding.searchBar.text = binding.searchView.text
                binding.searchView.hide()
                false
            }*/
        binding.rvTrending.adapter = trendingAdapter

        // Trending
        lifecycleScope.launch {
            searchViewModel.trending.collectLatest { trending ->
                trendingAdapter.submitData(trending)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                trendingAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    binding.progressBarTrending.isVisible = state is LoadState.Loading
                }
            }
        }

        binding.rvTrending.adapter = trendingAdapter.withLoadStateFooter(
            TryAgainAdapter {
                trendingAdapter.retry()
            }
        )

        // trocar nestedScrollView para recyclerView e tentar colocar Header
        // Colocar trending
        // Fazer a busca com resultado dentro do search view
        // Tentar colocar sugestao no searchview
        // depois testar fora
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}