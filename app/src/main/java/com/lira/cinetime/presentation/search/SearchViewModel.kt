package com.lira.cinetime.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lira.cinetime.domain.search.GetTrendingUseCase

class SearchViewModel(getTrendingUseCase: GetTrendingUseCase) : ViewModel() {

    val trending = getTrendingUseCase().cachedIn(viewModelScope)

}