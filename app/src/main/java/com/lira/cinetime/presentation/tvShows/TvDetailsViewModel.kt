package com.lira.cinetime.presentation.tvShows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.lira.cinetime.data.models.firebase.TvShow
import com.lira.cinetime.data.models.tvShows.tvDetails.TvDetailsResponse
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.myLists.*
import com.lira.cinetime.domain.tvShows.tvDetails.GetTvDetailsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TvDetailsViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                         private val getTvDetailsUseCase: GetTvDetailsUseCase,
                         private val addTvToFavoritesUseCase: AddTvToFavoritesUseCase,
                         private val isTvFavoriteUseCase: IsTvFavoriteUseCase,
                         private val deleteFavoriteTvUseCase: DeleteFavoriteTvUseCase,
                         private val addTvToWatchUseCase: AddTvToWatchUseCase,
                         private val isTvInToWatchUseCase: IsTvInToWatchUseCase,
                         private val deleteToWatchTvUseCase: DeleteToWatchTvUseCase) : ViewModel() {

    private val _tvDetails = MutableStateFlow<State>(State.Loading)
    val tvDetails = _tvDetails.asStateFlow()

    private val _dbOperations = MutableStateFlow<DBState>(DBState.LoadingDB)
    val dbOperations = _dbOperations.asStateFlow()

    private var user: FirebaseUser? = null

    init {
        viewModelScope.launch {
            getCurrentUserUseCase().collect {
                user = it
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("TvDetails", "Caught: $throwable")
    }

    fun getTvDetails(tvId: Long){
        viewModelScope.launch(handler) {
            runCatching {
                getTvDetailsUseCase(tvId)
            }.onSuccess { result ->
                _tvDetails.value = State.Success(result)
            }.onFailure {
                _tvDetails.value = State.Error(it)
            }
        }
    }

    // Favorites
    fun checkIfFavorite(tvId: Long) {
        viewModelScope.launch(handler) {
            runCatching {
                isTvFavoriteUseCase(tvId, user!!.uid)
            }.onSuccess { result ->
                _dbOperations.value = DBState.IsFavorite(result)
            }.onFailure {
                _dbOperations.value = DBState.ErrorDB(it)
            }
        }
    }

    fun addToFavorites(tvId: Long, name: String, posterPath: String?) {

        val tv = TvShow(user!!.uid, tvId, name, posterPath)

        viewModelScope.launch {
            addTvToFavoritesUseCase(tv)
                .catch {
                    _dbOperations.value = DBState.AddFavoriteFailure(it)
                }
                .collect {
                    _dbOperations.value = DBState.AddFavoriteSuccess(it)
                }
        }
    }

    fun deleteFavoriteTv(tvId: Long) {
        viewModelScope.launch {
            deleteFavoriteTvUseCase(tvId, user!!.uid)
        }
    }

    // To Watch
    fun checkIfInToWatch(tvId: Long) {
        viewModelScope.launch(handler) {
            runCatching {
                isTvInToWatchUseCase(tvId, user!!.uid)
            }.onSuccess { result ->
                _dbOperations.value = DBState.IsInToWatch(result)
            }.onFailure {
                _dbOperations.value = DBState.ErrorDB(it)
            }
        }
    }

    fun addToWatchList(tvId: Long, name: String, posterPath: String?) {

        val tv = TvShow(user!!.uid, tvId, name, posterPath)

        viewModelScope.launch {
            addTvToWatchUseCase(tv)
                .catch {
                    _dbOperations.value = DBState.AddToWatchFailure(it)
                }
                .collect {
                    _dbOperations.value = DBState.AddToWatchSuccess(it)
                }
        }

    }

    fun deleteTvInToWatch(tvId: Long) {
        viewModelScope.launch {
            deleteToWatchTvUseCase(tvId, user!!.uid)
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val tvDetails: TvDetailsResponse): State()
        data class Error(val error: Throwable): State()
    }

    sealed class DBState {
        object LoadingDB: DBState()
        data class IsFavorite(val favorite: Boolean): DBState()
        data class AddFavoriteSuccess(val document: DocumentReference): DBState()
        data class AddFavoriteFailure(val error: Throwable): DBState()
        data class IsInToWatch(val inToWatch: Boolean): DBState()
        data class AddToWatchSuccess(val document: DocumentReference): DBState()
        data class AddToWatchFailure(val error: Throwable): DBState()
        data class ErrorDB(val error: Throwable): DBState()
    }

}