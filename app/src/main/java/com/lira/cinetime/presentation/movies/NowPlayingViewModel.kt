package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lira.cinetime.domain.nowPlaying.GetNowPlayingMoviesUseCase

class NowPlayingViewModel(getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase): ViewModel() {

    val nowPlayingMovies = getNowPlayingMoviesUseCase().cachedIn(viewModelScope)

}