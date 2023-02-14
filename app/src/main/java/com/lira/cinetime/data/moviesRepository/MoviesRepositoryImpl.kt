package com.lira.cinetime.data.moviesRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lira.cinetime.data.models.nowPlaying.NowPlayingResult
import com.lira.cinetime.data.models.popularMovies.PopularMoviesResult
import com.lira.cinetime.data.models.topRated.TopRatedResult
import com.lira.cinetime.data.services.TheMoviesService
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(private val service: TheMoviesService): MoviesRepository {

    override fun getPopularMovies(): Flow<PagingData<PopularMoviesResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(service = service)
            }
        ).flow
    }

    override fun getNowPlayingMovies(): Flow<PagingData<NowPlayingResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NowPlayingPagingSource(service = service)
            }
        ).flow
    }

    override fun getTopRatedMovies(): Flow<PagingData<TopRatedResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopRatedPagingSource(service = service)
            }
        ).flow
    }

}