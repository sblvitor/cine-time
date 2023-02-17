package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lira.cinetime.data.models.movieDetails.MovieDetailsResponse
import com.lira.cinetime.domain.movieDetails.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) : ViewModel() {

    private val _movieDetails = MutableSharedFlow<State>()
    val movieDetails: SharedFlow<State> = _movieDetails

    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId)
                .onStart {
                    _movieDetails.emit(State.Loading)
                } .catch {
                    _movieDetails.emit(State.Error(it))
                } .collect {
                    _movieDetails.emit(State.Success(it))
                }
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val movieDetails: MovieDetailsResponse): State()
        data class Error(val error: Throwable): State()
    }

}