package com.lira.cinetime.presentation.tvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lira.cinetime.data.models.tvShows.tvDetails.TvDetailsResponse
import com.lira.cinetime.domain.tvShows.tvDetails.GetTvDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TvDetailsViewModel(private val getTvDetailsUseCase: GetTvDetailsUseCase) : ViewModel() {

    private val _tvDetails = MutableStateFlow<State>(State.Loading)
    val tvDetails = _tvDetails.asStateFlow()

    fun getTvDetails(tvId: Long){
        viewModelScope.launch {
            getTvDetailsUseCase(tvId)
                .catch {
                    _tvDetails.value = State.Error(it)
                } .collect {
                    _tvDetails.value = State.Success(it)
                }
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val tvDetails: TvDetailsResponse): State()
        data class Error(val error: Throwable): State()
    }

}