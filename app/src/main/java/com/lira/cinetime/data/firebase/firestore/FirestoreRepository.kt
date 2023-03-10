package com.lira.cinetime.data.firebase.firestore

import com.google.firebase.firestore.DocumentReference
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.firebase.TvShow
import com.lira.cinetime.data.models.firebase.User
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun addUser(user: User)

    fun getUser(userId: String): Flow<User>

    suspend fun updateUserProfileImage(userId: String, profileImg: String)

    suspend fun updateUserName(userId: String, name: String)

    suspend fun updateUserProfileImgAndName(userId: String, profileImg: String, name: String)

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