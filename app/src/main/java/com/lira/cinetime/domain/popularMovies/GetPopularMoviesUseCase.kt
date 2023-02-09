package com.lira.cinetime.domain.popularMovies

import androidx.paging.PagingData
import com.lira.cinetime.data.models.PopularMoviesResult
import com.lira.cinetime.data.moviesRepository.PopularMoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val popularMoviesRepository: PopularMoviesRepository) {

    operator fun invoke(): Flow<PagingData<PopularMoviesResult>> {
        return popularMoviesRepository.getPopularMovies()
    }

}