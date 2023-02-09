package com.lira.cinetime.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.firebase.auth.FirebaseUser
import com.lira.cinetime.data.models.PopularMoviesResult
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.popularMovies.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PopularMoviesViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                             private val getPopularMoviesUseCase: GetPopularMoviesUseCase): ViewModel() {

    private val _isConnected = MutableStateFlow<FirebaseUser?>(null)
    val isConnected = _isConnected.asStateFlow()

    private val _popularMovies = MutableStateFlow<State>(State.Loading)
    val popularMovies = _popularMovies.asStateFlow()

    init {
        checkIfConnected()
    }

    private fun checkIfConnected() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect {
                _isConnected.value = it
            }
        }
    }

    fun getPopularMovies(): Flow<PagingData<PopularMoviesResult>> {
        return getPopularMoviesUseCase().cachedIn(viewModelScope)
    }

    sealed class State {
        object Loading: State()
        data class Success(val popularMovies: PagingData<PopularMoviesResult>): State()
        data class Error(val error: Throwable): State()
    }

}