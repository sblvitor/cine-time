package com.lira.cinetime.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lira.cinetime.data.models.PopularMoviesResult
import com.lira.cinetime.domain.popularMovies.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow

class PopularMoviesViewModel(private val getPopularMoviesUseCase: GetPopularMoviesUseCase): ViewModel() {

    fun getPopularMovies(): Flow<PagingData<PopularMoviesResult>> {
        return getPopularMoviesUseCase().cachedIn(viewModelScope)
    }

}