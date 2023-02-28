package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.movies.movieDetails.GetMovieDetailsUseCase
import com.lira.cinetime.domain.myLists.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(getCurrentUserUseCase: GetCurrentUserUseCase,
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
        user = getCurrentUserUseCase()
    }

    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId)
                .catch {
                    _movieDetails.value = State.Error(it)
                } .collect {
                    _movieDetails.value = State.Success(it)
                }
        }
    }

    // Favorites
    fun checkIfFavorite(movieId: Long) {
        viewModelScope.launch {
            isMovieFavoriteUseCase(movieId, user!!.uid)
                .catch {
                    _dbOperations.value = DBState.Error(it)
                }
                .collect {
                    if(it.documents.isNotEmpty())
                        _dbOperations.value = DBState.IsFavorite(true)
                    else
                        _dbOperations.value = DBState.IsFavorite(false)
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
        viewModelScope.launch {
            isMovieInToWatchUseCase(movieId, user!!.uid)
                .catch {
                    _dbOperations.value = DBState.Error(it)
                }
                .collect {
                    if(it.documents.isNotEmpty())
                        _dbOperations.value = DBState.IsInToWatch(true)
                    else
                        _dbOperations.value = DBState.IsInToWatch(false)
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