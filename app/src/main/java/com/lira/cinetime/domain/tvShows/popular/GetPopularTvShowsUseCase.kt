package com.lira.cinetime.domain.tvShows.popular

import androidx.paging.PagingData
import com.lira.cinetime.data.models.tvShows.popularTv.PopularTvResult
import com.lira.cinetime.data.repositories.tvShowsRepository.TvShowsRepository
import kotlinx.coroutines.flow.Flow

class GetPopularTvShowsUseCase(private val tvShowsRepository: TvShowsRepository) {

    operator fun invoke(): Flow<PagingData<PopularTvResult>> {
        return tvShowsRepository.getPopularTvShows()
    }

}