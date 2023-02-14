package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lira.cinetime.data.models.nowPlaying.NowPlayingResult
import com.lira.cinetime.domain.nowPlaying.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.flow.Flow

class NowPlayingViewModel(private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase): ViewModel() {

    fun getNowPlayingMovies(): Flow<PagingData<NowPlayingResult>> {
        return getNowPlayingMoviesUseCase().cachedIn(viewModelScope)
    }

}