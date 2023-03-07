package com.lira.cinetime.presentation.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.movies.movieDetails.GetMovieDetailsUseCase
import com.lira.cinetime.domain.myLists.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                            private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
                            private val isMovieFavoriteUseCase: IsMovieFavoriteUseCase,
                            private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
                            private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
                            private val addMovieToWatchUseCase: AddMovieToWatchUseCase,
                            private val isMovieInToWatchUseCase: IsMovieInToWatchUseCase,
                            private val deleteToWatchMovieUseCase: DeleteToWatchMovieUseCase) : ViewModel() {

    private val _movieDetails = MutableStateFlow<State>(State.Loading)
    val movieDetails: StateFlow<State> = _movieDetails

    private val _dbOperations = MutableStateFlow<DBState>(DBState.Loading)
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
        Log.e("MovieDetails", "Caught: $throwable")
    }

    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch(handler) {
            runCatching {
                getMovieDetailsUseCase(movieId)
            }.onSuccess { result ->
                _movieDetails.value = State.Success(result)
            }.onFailure {
                _movieDetails.value = State.Error(it)
            }
        }
    }

    // Favorites
    fun checkIfFavorite(movieId: Long) {
        viewModelScope.launch(handler) {
            runCatching {
                isMovieFavoriteUseCase(movieId, user!!.uid)
            }.onSuccess { result ->
                _dbOperations.value = DBState.IsFavorite(result)
            }.onFailure {
                _dbOperations.value = DBState.Error(it)
            }
        }
    }

    fun addToFavorites(movieId: Long, title: String, posterPath: String?) {

        val movie = Movie(user!!.uid, movieId, title, posterPath)

        viewModelScope.launch {
            addMovieToFavoritesUseCase(movie)
                .onStart {
                    _dbOperations.value = DBState.Loading
                }
                .catch {
                    _dbOperations.value = DBState.AddFavoriteFailure(it)
                }
                .collect {
                    _dbOperations.value = DBState.AddFavoriteSuccess(it)
                }
        }
    }

    fun deleteFavoriteMovie(movieId: Long) {
        viewModelScope.launch {
            deleteFavoriteMovieUseCase(movieId, user!!.uid)
        }
    }

    // To Watch
    fun checkIfInToWatch(movieId: Long) {
        viewModelScope.launch(handler) {
            runCatching {
                isMovieInToWatchUseCase(movieId, user!!.uid)
            }.onSuccess { result ->
                _dbOperations.value = DBState.IsInToWatch(result)
            }.onFailure {
                _dbOperations.value = DBState.Error(it)
            }
        }
    }

    fun addToWatchList(movieId: Long, title: String, posterPath: String?) {

        val movie = Movie(user!!.uid, movieId, title, posterPath)

        viewModelScope.launch {
            addMovieToWatchUseCase(movie)
                .catch {
                    _dbOperations.value = DBState.AddToWatchFailure(it)
                }
                .collect {
                    _dbOperations.value = DBState.AddToWatchSuccess(it)
                }
        }
    }

    fun deleteMovieInToWatch(movieId: Long) {
        viewModelScope.launch {
            deleteToWatchMovieUseCase(movieId, user!!.uid)
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val movieDetails: MovieDetailsResponse): State()
        data class Error(val error: Throwable): State()
    }

    sealed class DBState {
        object Loading: DBState()
        data class IsFavorite(val favorite: Boolean): DBState()
        data class AddFavoriteSuccess(val document: DocumentReference): DBState()
        data class AddFavoriteFailure(val error: Throwable): DBState()
        data class IsInToWatch(val inToWatch: Boolean): DBState()
        data class AddToWatchSuccess(val document: DocumentReference): DBState()
        data class AddToWatchFailure(val error: Throwable): DBState()
        data class Error(val error: Throwable): DBState()
    }

}