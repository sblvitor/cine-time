package com.lira.cinetime.data.firebase.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.firebase.TvShow
import com.lira.cinetime.data.models.firebase.User
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun addUser(user: User)

    suspend fun getUser(userId: String): DocumentSnapshot?

    fun addMovieToFavorites(movie: Movie): Flow<DocumentReference>

    suspend fun isThisMovieFavorite(movieId: Long, userId: String): Boolean

    suspend fun deleteFavoriteMovie(movieId: Long, userId: String)

    fun addMovieToWatchList(movie: Movie): Flow<DocumentReference>

    suspend fun isThisMovieInToWatch(movieId: Long, userId: String): Boolean

    suspend fun deleteToWatchMovie(movieId: Long, userId: String)


    fun addTvToFavorites(tvShow: TvShow): Flow<DocumentReference>

    suspend fun isThisTvFavorite(tvId: Long, userId: String): Boolean

    suspend fun deleteFavoriteTv(tvId: Long, userId: String)

    fun addTvToWatchList(tvShow: TvShow): Flow<DocumentReference>

    suspend fun isThisTvInToWatch(tvId: Long, userId: String): Boolean

    suspend fun deleteToWatchTv(tvId: Long, userId: String)

    fun getAllFavoriteMovies(userId: String): Flow<List<Movie>>

    fun getAllToWatchMovies(userId: String): Flow<List<Movie>>

    fun getAllFavoriteTv(userId: String): Flow<List<TvShow>>

    fun getAllToWatchTv(userId: String): Flow<List<TvShow>>

}