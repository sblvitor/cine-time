package com.lira.cinetime.presentation.tvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lira.cinetime.domain.tvShows.topRated.GetTopRatedTvUseCase

class TopRatedTvViewModel(getTopRatedTvUseCase: GetTopRatedTvUseCase) : ViewModel() {

    val topRatedTv = getTopRatedTvUseCase().cachedIn(viewModelScope)

}