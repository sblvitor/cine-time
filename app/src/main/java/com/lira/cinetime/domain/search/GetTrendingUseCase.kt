package com.lira.cinetime.domain.search

import androidx.paging.PagingData
import com.lira.cinetime.data.models.search.trending.TrendingResult
import com.lira.cinetime.data.repositories.searchRepository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingUseCase(private val searchRepository: SearchRepository) {

    operator fun invoke(): Flow<PagingData<TrendingResult>> {
        return searchRepository.getTrending()
    }

}