package com.lira.cinetime.domain.popularMovies

import androidx.paging.PagingData
import com.lira.cinetime.data.models.popularMovies.PopularMoviesResult
import com.lira.cinetime.data.moviesRepository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): Flow<PagingData<PopularMoviesResult>> {
        return moviesRepository.getPopularMovies()
    }

}