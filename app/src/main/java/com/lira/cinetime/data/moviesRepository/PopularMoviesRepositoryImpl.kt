package com.lira.cinetime.data.moviesRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lira.cinetime.data.models.PopularMoviesResult
import com.lira.cinetime.data.services.TheMoviesService
import kotlinx.coroutines.flow.Flow

class PopularMoviesRepositoryImpl(private val service: TheMoviesService): PopularMoviesRepository {

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

}