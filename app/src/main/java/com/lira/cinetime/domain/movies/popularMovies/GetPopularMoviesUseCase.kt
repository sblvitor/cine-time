package com.lira.cinetime.domain.movies.popularMovies

import androidx.paging.PagingData
import com.lira.cinetime.data.models.movies.popularMovies.PopularMoviesResult
import com.lira.cinetime.data.repositories.moviesRepository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): Flow<PagingData<PopularMoviesResult>> {
        return moviesRepository.getPopularMovies()
    }

}