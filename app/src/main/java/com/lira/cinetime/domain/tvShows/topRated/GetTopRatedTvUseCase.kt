package com.lira.cinetime.domain.tvShows.topRated

import androidx.paging.PagingData
import com.lira.cinetime.data.models.tvShows.topRated.TopRatedTvResult
import com.lira.cinetime.data.repositories.tvShowsRepository.TvShowsRepository
import kotlinx.coroutines.flow.Flow

class GetTopRatedTvUseCase(private val tvShowsRepository: TvShowsRepository) {

    operator fun invoke(): Flow<PagingData<TopRatedTvResult>> {
        return tvShowsRepository.getTopRatedTv()
    }

}