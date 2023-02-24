package com.lira.cinetime.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.lira.cinetime.R
import com.lira.cinetime.core.createProgressDialog
import com.lira.cinetime.databinding.FragmentSearchBinding
import com.lira.cinetime.presentation.search.SearchViewModel
import com.lira.cinetime.ui.tryAgainUtil.TryAgainAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val searchViewModel by viewModel<SearchViewModel>()
    private val trendingAdapter by lazy { TrendingAdapter() }
    private val searchAdapter by lazy { SearchAdapter() }
    private val dialog by lazy { createProgressDialog() }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationView.repeatCount = 2
        //binding.searchView.setupWithSearchBar(binding.searchBar)
        /*binding.searchView
            .editText
            .setOnEditorActionListener { textView, i, keyEvent ->
                binding.searchBar.text = binding.searchView.text
                binding.searchView.hide()
                false
            }*/
        binding.rvTrending.adapter = trendingAdapter
        binding.rvSearch.adapter = searchAdapter

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

        binding.rvSearch.adapter = searchAdapter.withLoadStateFooter(
            TryAgainAdapter {
                searchAdapter.retry()
            }
        )

        setupSearch()

        // trocar nestedScrollView para recyclerView e tentar colocar Header
        // Colocar trending
        // Fazer a busca com resultado dentro do search view
        // Tentar colocar sugestao no searchview
        // depois testar fora
    }

    private fun setupSearch() {
        val searchView = binding.searchView

        searchView
            .editText
            .setOnEditorActionListener { textView, _, _ ->
                if(textView.text.isNotEmpty()) {
                    binding.animationView.visibility = View.GONE
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.show()
                    lifecycleScope.launch {
                        searchViewModel.search(textView.text.toString()).collectLatest { results ->
                            dialog.dismiss()
                            searchAdapter.submitData(results)
                        }
                    }
                } else {
                    Snackbar.make(binding.root, getString(R.string.search_something), Snackbar.LENGTH_SHORT).show()
                }
                false
            }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(searchView.isShowing){
                    searchView.hide()
                } else
                    this.isEnabled = false
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}