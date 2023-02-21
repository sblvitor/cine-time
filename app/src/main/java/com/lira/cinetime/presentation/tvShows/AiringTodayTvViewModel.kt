package com.lira.cinetime.presentation.tvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lira.cinetime.domain.tvShows.airingToday.GetAiringTodayTvUseCase

class AiringTodayTvViewModel(getAiringTodayTvUseCase: GetAiringTodayTvUseCase) : ViewModel() {

    val airingToday = getAiringTodayTvUseCase().cachedIn(viewModelScope)

}