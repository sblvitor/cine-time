package com.lira.cinetime.domain.search

import androidx.paging.PagingData
import com.lira.cinetime.data.models.search.multiSearch.MultiSearchResult
import com.lira.cinetime.data.repositories.searchRepository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetMultiSearchResultUseCase(private val searchRepository: SearchRepository) {

    operator fun invoke(query: String): Flow<PagingData<MultiSearchResult>> {
        return searchRepository.searchMulti(query)
    }

}