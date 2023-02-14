package com.lira.cinetime.data.moviesRepository

import androidx.paging.PagingData
import com.lira.cinetime.data.models.nowPlaying.NowPlayingResult
import com.lira.cinetime.data.models.popularMovies.PopularMoviesResult
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovies(): Flow<PagingData<PopularMoviesResult>>

    fun getNowPlayingMovies(): Flow<PagingData<NowPlayingResult>>

}