package com.lira.cinetime.domain.movies.movieDetails

import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.data.repositories.moviesRepository.MoviesRepository

class GetMovieDetailsUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movieId: Long): MovieDetailsResponse {
        return moviesRepository.getMovieDetails(movieId)
    }

}