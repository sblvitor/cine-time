package com.lira.cinetime.data.repositories.searchRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lira.cinetime.data.models.search.multiSearch.MultiSearchResult
import com.lira.cinetime.data.models.search.trending.TrendingResult
import com.lira.cinetime.data.services.TheMoviesService
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(private val service: TheMoviesService): SearchRepository {

    override fun getTrending(): Flow<PagingData<TrendingResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TrendingPagingSource(service = service)
            }
        ).flow
    }

    override fun searchMulti(query: String): Flow<PagingData<MultiSearchResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MultiSearchPagingSource(service = service, query)
            }
        ).flow
    }

}