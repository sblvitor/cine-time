package com.lira.cinetime.domain.movies.movieDetails

import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.data.repositories.moviesRepository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(movieId: Long): Flow<MovieDetailsResponse> {
        return moviesRepository.getMovieDetails(movieId)
    }

}