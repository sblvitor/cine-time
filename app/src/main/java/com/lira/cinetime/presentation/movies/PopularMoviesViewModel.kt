package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lira.cinetime.domain.movies.popularMovies.GetPopularMoviesUseCase

class PopularMoviesViewModel(getPopularMoviesUseCase: GetPopularMoviesUseCase): ViewModel() {

    val popularMovies = getPopularMoviesUseCase().cachedIn(viewModelScope)

}