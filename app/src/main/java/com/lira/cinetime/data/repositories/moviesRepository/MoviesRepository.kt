package com.lira.cinetime.data.repositories.moviesRepository

import androidx.paging.PagingData
import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.data.models.movies.nowPlaying.NowPlayingResult
import com.lira.cinetime.data.models.movies.popularMovies.PopularMoviesResult
import com.lira.cinetime.data.models.movies.topRated.TopRatedResult
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovies(): Flow<PagingData<PopularMoviesResult>>

    fun getNowPlayingMovies(): Flow<PagingData<NowPlayingResult>>

    fun getTopRatedMovies(): Flow<PagingData<TopRatedResult>>

    fun getMovieDetails(movieId: Long): Flow<MovieDetailsResponse>

}