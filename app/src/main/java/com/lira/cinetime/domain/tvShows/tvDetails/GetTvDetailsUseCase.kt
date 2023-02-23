package com.lira.cinetime.domain.tvShows.tvDetails

import com.lira.cinetime.data.models.tvShows.tvDetails.TvDetailsResponse
import com.lira.cinetime.data.repositories.tvShowsRepository.TvShowsRepository
import kotlinx.coroutines.flow.Flow

class GetTvDetailsUseCase(private val tvShowsRepository: TvShowsRepository) {

    operator fun invoke(tvId: Long): Flow<TvDetailsResponse> {
        return tvShowsRepository.getTvDetails(tvId)
    }

}