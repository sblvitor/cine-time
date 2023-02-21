package com.lira.cinetime.data.repositories.moviesRepository

import android.os.RemoteException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lira.cinetime.core.Constants
import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.data.models.movies.nowPlaying.NowPlayingResult
import com.lira.cinetime.data.models.movies.popularMovies.PopularMoviesResult
import com.lira.cinetime.data.models.movies.topRated.TopRatedResult
import com.lira.cinetime.data.services.TheMoviesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

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

    override fun getMovieDetails(movieId: Long): Flow<MovieDetailsResponse> {
        return flow {
            try {
                emit(service.getMovieDetails(
                    movieId = movieId,
                    api_key = Constants.API_KEY,
                    language = Constants.LANG_PT_BR,
                    append_to_response = "watch/providers,credits"))
            } catch (e: HttpException) {
                throw RemoteException(e.message)
            }
        }
    }

}