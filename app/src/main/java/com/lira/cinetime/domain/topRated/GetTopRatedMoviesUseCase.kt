package com.lira.cinetime.domain.topRated

import androidx.paging.PagingData
import com.lira.cinetime.data.models.topRated.TopRatedResult
import com.lira.cinetime.data.moviesRepository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetTopRatedMoviesUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): Flow<PagingData<TopRatedResult>> {
        return moviesRepository.getTopRatedMovies()
    }

}