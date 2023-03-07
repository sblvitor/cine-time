package com.lira.cinetime.presentation.myList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lira.cinetime.data.models.firebase.AllData
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.firebase.TvShow
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.myLists.GetAllFavoriteMoviesUseCase
import com.lira.cinetime.domain.myLists.GetAllFavoriteTvUseCase
import com.lira.cinetime.domain.myLists.GetAllToWatchMoviesUseCase
import com.lira.cinetime.domain.myLists.GetAllToWatchTvUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class MyListViewModel(getCurrentUserUseCase: GetCurrentUserUseCase,
                      private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase,
                      private val getAllToWatchMoviesUseCase: GetAllToWatchMoviesUseCase,
                      private val getAllFavoriteTvUseCase: GetAllFavoriteTvUseCase,
                      private val getAllToWatchTvUseCase: GetAllToWatchTvUseCase) : ViewModel() {

    private val userId: Flow<String> = getCurrentUserUseCase().map { user -> user!!.uid }

    @OptIn(ExperimentalCoroutinesApi::class)
    val allData: StateFlow<State> = userId.flatMapLatest { newUserId ->
        combine(
        getAllToWatchMovies(newUserId),
        getAllFavoriteMovies(newUserId),
        getAllToWatchTv(newUserId),
        getAllFavoriteTv(newUserId)
    ) { toWatchMovies, favMovies, toWatchTv, favTv ->
        State.Success(AllData(toWatchMovies, favMovies, toWatchTv, favTv))
    } }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = State.Loading
    )

    private fun getAllToWatchMovies(userId: String): Flow<List<Movie>> {
        Log.e("TAG", userId)
        return getAllToWatchMoviesUseCase(userId)
    }

    private fun getAllFavoriteMovies(userId: String): Flow<List<Movie>> {
        return getAllFavoriteMoviesUseCase(userId)
    }

    private fun getAllToWatchTv(userId: String): Flow<List<TvShow>> {
        return getAllToWatchTvUseCase(userId)
    }

    private fun getAllFavoriteTv(userId: String): Flow<List<TvShow>> {
        return getAllFavoriteTvUseCase(userId)
    }

    sealed class State {
        object Loading: State()
        data class Success(val result: AllData): State()
    }

}