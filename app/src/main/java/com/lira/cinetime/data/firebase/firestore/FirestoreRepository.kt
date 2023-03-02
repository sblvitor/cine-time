package com.lira.cinetime.data.firebase.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.lira.cinetime.data.models.firebase.*
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun addUser(user: User)

    fun addMovieToFavorites(movie: Movie): Flow<DocumentReference>

    fun isThisMovieFavorite(movieId: Long, userId: String): Flow<QuerySnapshot>

    suspend fun deleteFavoriteMovie(movieId: Long, userId: String)

    fun addMovieToWatchList(movie: Movie): Flow<DocumentReference>

    fun isThisMovieInToWatch(movieId: Long, userId: String): Flow<QuerySnapshot>

    suspend fun deleteToWatchMovie(movieId: Long, userId: String)


    fun addTvToFavorites(tvShow: TvShow): Flow<DocumentReference>

    fun isThisTvFavorite(tvId: Long, userId: String): Flow<QuerySnapshot>

    suspend fun deleteFavoriteTv(tvId: Long, userId: String)

    fun addTvToWatchList(tvShow: TvShow): Flow<DocumentReference>

    fun isThisTvInToWatch(tvId: Long, userId: String): Flow<QuerySnapshot>

    suspend fun deleteToWatchTv(tvId: Long, userId: String)

    fun getAllFavoriteMovies(userId: String): Flow<List<Movie>>

    fun getAllToWatchMovies(userId: String): Flow<List<Movie>>

    fun getAllFavoriteTv(userId: String): Flow<List<TvShow>>

    fun getAllToWatchTv(userId: String): Flow<List<TvShow>>

}