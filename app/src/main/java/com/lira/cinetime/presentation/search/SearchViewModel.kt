package com.lira.cinetime.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lira.cinetime.data.models.search.multiSearch.MultiSearchResult
import com.lira.cinetime.domain.search.GetMultiSearchResultUseCase
import com.lira.cinetime.domain.search.GetTrendingUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(getTrendingUseCase: GetTrendingUseCase,
                      private val getMultiSearchResultUseCase: GetMultiSearchResultUseCase) : ViewModel() {

    val trending = getTrendingUseCase().cachedIn(viewModelScope)

    fun search(query: String): Flow<PagingData<MultiSearchResult>> {
        return getMultiSearchResultUseCase(query).cachedIn(viewModelScope)
    }

}