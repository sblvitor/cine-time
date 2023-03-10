package com.lira.cinetime.data.repositories.searchRepository

import androidx.paging.PagingData
import com.lira.cinetime.data.models.search.multiSearch.MultiSearchResult
import com.lira.cinetime.data.models.search.trending.TrendingResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getTrending(): Flow<PagingData<TrendingResult>>

    fun searchMulti(query: String): Flow<PagingData<MultiSearchResult>>

}