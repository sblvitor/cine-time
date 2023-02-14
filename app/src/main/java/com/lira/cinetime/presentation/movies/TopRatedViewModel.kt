package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lira.cinetime.data.models.topRated.TopRatedResult
import com.lira.cinetime.domain.topRated.GetTopRatedMoviesUseCase
import kotlinx.coroutines.flow.Flow

class TopRatedViewModel(private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase) : ViewModel() {

    fun getTopRatedMovies(): Flow<PagingData<TopRatedResult>> {
        return getTopRatedMoviesUseCase().cachedIn(viewModelScope)
    }

}