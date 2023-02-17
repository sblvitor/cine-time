package com.lira.cinetime.domain.movieDetails

import com.lira.cinetime.data.models.movieDetails.MovieDetailsResponse
import com.lira.cinetime.data.moviesRepository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(movieId: Long): Flow<MovieDetailsResponse> {
        return moviesRepository.getMovieDetails(movieId)
    }

}