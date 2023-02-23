package com.lira.cinetime.data.repositories.tvShowsRepository

import android.os.RemoteException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lira.cinetime.core.Constants
import com.lira.cinetime.data.models.tvShows.airingToday.AiringTodayTvResult
import com.lira.cinetime.data.models.tvShows.popularTv.PopularTvResult
import com.lira.cinetime.data.models.tvShows.topRated.TopRatedTvResult
import com.lira.cinetime.data.models.tvShows.tvDetails.TvDetailsResponse
import com.lira.cinetime.data.services.TheMoviesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

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

    override fun getAiringTodayTv(): Flow<PagingData<AiringTodayTvResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AiringTodayTvPagingSource(service = service)
            }
        ).flow
    }

    override fun getTopRatedTv(): Flow<PagingData<TopRatedTvResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopRatedTvPagingSource(service = service)
            }
        ).flow
    }

    override fun getTvDetails(tvId: Long): Flow<TvDetailsResponse> {
        return flow {
            try {
                emit(service.getTvDetails(
                    tvId = tvId,
                    api_key = Constants.API_KEY,
                    language = Constants.LANG_PT_BR,
                    append_to_response = "watch/providers,aggregate_credits"))
            } catch (e: HttpException) {
                throw RemoteException(e.message)
            }
        }
    }
}