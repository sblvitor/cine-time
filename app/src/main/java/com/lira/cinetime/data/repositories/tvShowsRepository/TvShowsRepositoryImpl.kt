package com.lira.cinetime.data.repositories.tvShowsRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lira.cinetime.data.models.tvShows.popularTv.PopularTvResult
import com.lira.cinetime.data.services.TheMoviesService
import kotlinx.coroutines.flow.Flow

class TvShowsRepositoryImpl(private val service: TheMoviesService): TvShowsRepository {

    override fun getPopularTvShows(): Flow<PagingData<PopularTvResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularTvShowsPagingSource(service = service)
            }
        ).flow
    }
}