package com.lira.cinetime.data.repositories.tvShowsRepository

import androidx.paging.PagingData
import com.lira.cinetime.data.models.tvShows.popularTv.PopularTvResult
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {

    fun getPopularTvShows(): Flow<PagingData<PopularTvResult>>

}