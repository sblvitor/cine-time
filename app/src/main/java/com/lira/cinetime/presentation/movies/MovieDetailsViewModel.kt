package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.movies.movieDetails.MovieDetailsResponse
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.movies.movieDetails.GetMovieDetailsUseCase
import com.lira.cinetime.domain.myLists.AddMovieToFavoritesUseCase
import com.lira.cinetime.domain.myLists.DeleteFavoriteMovieUseCase
import com.lira.cinetime.domain.myLists.IsMovieFavoriteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
                            private val isMovieFavoriteUseCase: IsMovieFavoriteUseCase,
                            private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
                            private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase) : ViewModel() {

    private val _movieDetails = MutableStateFlow<State>(State.Loading)
    val movieDetails: StateFlow<State> = _movieDetails

    private val _dbOperations = MutableStateFlow<DBState>(DBState.Loading)
    val dbOperations = _dbOperations.asStateFlow()

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

    fun checkIfFavorite(movieId: Long) {
        // Refatorar
        val user = Firebase.auth.currentUser

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

    fun addMovieToFavorites(movieId: Long, title: String, posterPath: String?) {
        // Refatorar
        val user = Firebase.auth.currentUser

        val movie = Movie(user!!.uid, movieId, title, posterPath)
        viewModelScope.launch {
            addMovieToFavoritesUseCase(movie)
                .onStart {
                    _dbOperations.value = DBState.Loading
                }
                .catch {
                    _dbOperations.value = DBState.AddFailure(it)
                }
                .collect {
                    _dbOperations.value = DBState.AddSuccess(it)
                }
        }
    }

    fun deleteFavoriteMovie(movieId: Long) {
        // Refatorar
        val user = Firebase.auth.currentUser

        viewModelScope.launch {
            deleteFavoriteMovieUseCase(movieId, user!!.uid)
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
        data class AddSuccess(val document: DocumentReference): DBState()
        data class AddFailure(val error: Throwable): DBState()
        data class Error(val error: Throwable): DBState()
    }

}