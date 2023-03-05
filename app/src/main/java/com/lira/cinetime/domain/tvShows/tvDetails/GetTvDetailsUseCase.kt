package com.lira.cinetime.domain.tvShows.tvDetails

import com.lira.cinetime.data.models.tvShows.tvDetails.TvDetailsResponse
import com.lira.cinetime.data.repositories.tvShowsRepository.TvShowsRepository

class GetTvDetailsUseCase(private val tvShowsRepository: TvShowsRepository) {

    suspend operator fun invoke(tvId: Long): TvDetailsResponse {
        return tvShowsRepository.getTvDetails(tvId)
    }

}