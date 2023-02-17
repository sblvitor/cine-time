package com.lira.cinetime.data.moviesRepository

import androidx.paging.PagingData
import com.lira.cinetime.data.models.movieDetails.MovieDetailsResponse
import com.lira.cinetime.data.models.nowPlaying.NowPlayingResult
import com.lira.cinetime.data.models.popularMovies.PopularMoviesResult
import com.lira.cinetime.data.models.topRated.TopRatedResult
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovies(): Flow<PagingData<PopularMoviesResult>>

    fun getNowPlayingMovies(): Flow<PagingData<NowPlayingResult>>

    fun getTopRatedMovies(): Flow<PagingData<TopRatedResult>>

    fun getMovieDetails(movieId: Long): Flow<MovieDetailsResponse>

}