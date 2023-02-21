package com.lira.cinetime.domain.tvShows.airingToday

import androidx.paging.PagingData
import com.lira.cinetime.data.models.tvShows.airingToday.AiringTodayTvResult
import com.lira.cinetime.data.repositories.tvShowsRepository.TvShowsRepository
import kotlinx.coroutines.flow.Flow

class GetAiringTodayTvUseCase(private val tvShowsRepository: TvShowsRepository) {

    operator fun invoke(): Flow<PagingData<AiringTodayTvResult>> {
        return tvShowsRepository.getAiringTodayTv()
    }

}