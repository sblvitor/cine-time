package com.lira.cinetime.presentation.tvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lira.cinetime.domain.tvShows.popular.GetPopularTvShowsUseCase

class PopularTvViewModel(getPopularTvShowsUseCase: GetPopularTvShowsUseCase) : ViewModel() {

    val popularTv = getPopularTvShowsUseCase().cachedIn(viewModelScope)

}