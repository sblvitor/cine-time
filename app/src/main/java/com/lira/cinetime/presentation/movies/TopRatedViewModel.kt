package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lira.cinetime.domain.topRated.GetTopRatedMoviesUseCase

class TopRatedViewModel(getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase) : ViewModel() {

    val topRatedMovies = getTopRatedMoviesUseCase().cachedIn(viewModelScope)

}