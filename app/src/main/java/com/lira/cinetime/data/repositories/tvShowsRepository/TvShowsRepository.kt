package com.lira.cinetime.data.repositories.tvShowsRepository

import androidx.paging.PagingData
import com.lira.cinetime.data.models.tvShows.airingToday.AiringTodayTvResult
import com.lira.cinetime.data.models.tvShows.popularTv.PopularTvResult
import com.lira.cinetime.data.models.tvShows.topRated.TopRatedTvResult
import com.lira.cinetime.data.models.tvShows.tvDetails.TvDetailsResponse
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {

    fun getPopularTvShows(): Flow<PagingData<PopularTvResult>>

    fun getAiringTodayTv(): Flow<PagingData<AiringTodayTvResult>>

    fun getTopRatedTv(): Flow<PagingData<TopRatedTvResult>>

    fun getTvDetails(tvId: Long): Flow<TvDetailsResponse>

}