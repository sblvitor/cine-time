package com.lira.cinetime.data.moviesRepository

import androidx.paging.PagingData
import com.lira.cinetime.data.models.PopularMoviesResult
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {

    fun getPopularMovies(): Flow<PagingData<PopularMoviesResult>>

}