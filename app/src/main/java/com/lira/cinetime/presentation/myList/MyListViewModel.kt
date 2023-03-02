package com.lira.cinetime.presentation.myList

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.lira.cinetime.data.models.firebase.AllData
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.firebase.TvShow
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.myLists.GetAllFavoriteMoviesUseCase
import com.lira.cinetime.domain.myLists.GetAllFavoriteTvUseCase
import com.lira.cinetime.domain.myLists.GetAllToWatchMoviesUseCase
import com.lira.cinetime.domain.myLists.GetAllToWatchTvUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class MyListViewModel(getCurrentUserUseCase: GetCurrentUserUseCase,
                      private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase,
                      private val getAllToWatchMoviesUseCase: GetAllToWatchMoviesUseCase,
                      private val getAllFavoriteTvUseCase: GetAllFavoriteTvUseCase,
                      private val getAllToWatchTvUseCase: GetAllToWatchTvUseCase) : ViewModel() {

    private var user: FirebaseUser? = null

    init {
        user = getCurrentUserUseCase()
    }

    val allData: Flow<AllData> = combine(
        getAllToWatchMovies(),
        getAllFavoriteMovies(),
        getAllToWatchTv(),
        getAllFavoriteTv()
    ) { toWatchMovies, favMovies, toWatchTv, favTv ->
        AllData(toWatchMovies, favMovies, toWatchTv, favTv)
    }

    private fun getAllToWatchMovies(): Flow<List<Movie>> {
        return getAllToWatchMoviesUseCase(user!!.uid)
    }

    private fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return getAllFavoriteMoviesUseCase(user!!.uid)
    }

    private fun getAllToWatchTv(): Flow<List<TvShow>> {
        return getAllToWatchTvUseCase(user!!.uid)
    }

    private fun getAllFavoriteTv(): Flow<List<TvShow>> {
        return getAllFavoriteTvUseCase(user!!.uid)
    }

}