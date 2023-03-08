package com.lira.cinetime.data.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.lira.cinetime.core.Constants
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.firebase.TvShow
import com.lira.cinetime.data.models.firebase.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreRepositoryImpl(private val db: FirebaseFirestore): FirestoreRepository {

    override suspend fun addUser(user: User) {
        db.collection(Constants.USERS)
            .document(user.id!!)
            .set(user)
            .addOnCompleteListener {
                Log.d("Register", "addUser: DocumentSnapshot successfully written!")
            }
            .addOnFailureListener {
                Log.w("Register", "addUser: Error adding document", it)
            }
    }

    override suspend fun getUser(userId: String): DocumentSnapshot? {
        val userRef = db.collection(Constants.USERS).document(userId)
        try {
            return userRef.get().await() ?: null
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    override suspend fun updateUserProfileImage(userId: String, profileImg: String) {
        val userRef = db.collection(Constants.USERS).document(userId)

        userRef.update("profileImage", profileImg).await()
    }

    override suspend fun updateUserName(userId: String, name: String) {
        val userRef = db.collection(Constants.USERS).document(userId)

        userRef.update("name", name).await()
    }

    override suspend fun updateUserProfileImgAndName(userId: String, profileImg: String, name: String) {
        val userRef = db.collection(Constants.USERS).document(userId)

        db.runBatch { batch ->
            batch.update(userRef, "profileImage", profileImg)
            batch.update(userRef, "name", name)
        }.await()
    }


    override fun addMovieToFavorites(movie: Movie) = flow {
        emit(db.collection(Constants.LISTS)
            .document(Constants.FAVORITES)
            .collection(Constants.MOVIES)
            .add(movie)
            .await())
    }

    override suspend fun isThisMovieFavorite(movieId: Long, userId: String): Boolean {
        try {
            val result = db.collection(Constants.LISTS)
                .document(Constants.FAVORITES)
                .collection(Constants.MOVIES)
                .whereEqualTo("movieId", movieId)
                .whereEqualTo("userID", userId)
                .get().await()

            if(result.documents.isEmpty())
                return false

            return true
        } catch (e: Exception) {
            throw Exception(e)
        }

    }

    override suspend fun deleteFavoriteMovie(movieId: Long, userId: String) {
        val movie = db.collection(Constants.LISTS)
            .document(Constants.FAVORITES)
            .collection(Constants.MOVIES)
            .whereEqualTo("movieId", movieId)
            .whereEqualTo("userID", userId)
            .get().await()

        movie.documents.forEach {
            it.reference
                .delete()
                .addOnSuccessListener {
                    Log.d("delete", "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener { e ->
                    Log.w("delete", "Error deleting document", e)
                }
        }
    }

    override fun addMovieToWatchList(movie: Movie) = flow {
        emit(db
            .collection(Constants.LISTS)
            .document(Constants.TO_WATCH)
            .collection(Constants.MOVIES)
            .add(movie)
            .await())
    }

    override suspend fun isThisMovieInToWatch(movieId: Long, userId: String): Boolean {
        try {
            val result = db.collection(Constants.LISTS)
                .document(Constants.TO_WATCH)
                .collection(Constants.MOVIES)
                .whereEqualTo("movieId", movieId)
                .whereEqualTo("userID", userId)
                .get().await()

            if(result.documents.isEmpty())
                return false

            return true
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    override suspend fun deleteToWatchMovie(movieId: Long, userId: String) {
        val movie = db.collection(Constants.LISTS)
            .document(Constants.TO_WATCH)
            .collection(Constants.MOVIES)
            .whereEqualTo("movieId", movieId)
            .whereEqualTo("userID", userId)
            .get().await()

        movie.documents.forEach {
            it.reference
                .delete()
                .addOnSuccessListener {
                    Log.d("delete", "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener { e ->
                    Log.w("delete", "Error deleting document", e)
                }
        }
    }

    override fun addTvToFavorites(tvShow: TvShow) = flow {
        emit(db.collection(Constants.LISTS)
            .document(Constants.FAVORITES)
            .collection(Constants.TV_SHOWS)
            .add(tvShow)
            .await())
    }

    override suspend fun isThisTvFavorite(tvId: Long, userId: String): Boolean {
        try {
            val result = db.collection(Constants.LISTS)
                .document(Constants.FAVORITES)
                .collection(Constants.TV_SHOWS)
                .whereEqualTo("tvId", tvId)
                .whereEqualTo("userID", userId)
                .get().await()

            if(result.documents.isEmpty())
                return false

            return true
        } catch (e: Exception){
            throw Exception(e)
        }
    }

    override suspend fun deleteFavoriteTv(tvId: Long, userId: String) {
        val tv = db.collection(Constants.LISTS)
            .document(Constants.FAVORITES)
            .collection(Constants.TV_SHOWS)
            .whereEqualTo("tvId", tvId)
            .whereEqualTo("userID", userId)
            .get().await()

        tv.documents.forEach {
            it.reference
                .delete()
                .addOnSuccessListener {
                    Log.d("delete", "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener { e ->
                    Log.w("delete", "Error deleting document", e)
                }
        }
    }

    override fun addTvToWatchList(tvShow: TvShow) = flow {
        emit(db.collection(Constants.LISTS)
            .document(Constants.TO_WATCH)
            .collection(Constants.TV_SHOWS)
            .add(tvShow)
            .await())
    }

    override suspend fun isThisTvInToWatch(tvId: Long, userId: String): Boolean {
        try {
            val result = db.collection(Constants.LISTS)
                .document(Constants.TO_WATCH)
                .collection(Constants.TV_SHOWS)
                .whereEqualTo("tvId", tvId)
                .whereEqualTo("userID", userId)
                .get().await()

            if(result.documents.isEmpty())
                return false

            return true
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    override suspend fun deleteToWatchTv(tvId: Long, userId: String) {
        val tv = db.collection(Constants.LISTS)
            .document(Constants.TO_WATCH)
            .collection(Constants.TV_SHOWS)
            .whereEqualTo("tvId", tvId)
            .whereEqualTo("userID", userId)
            .get().await()

        tv.documents.forEach {
            it.reference
                .delete()
                .addOnSuccessListener {
                    Log.d("delete", "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener { e ->
                    Log.w("delete", "Error deleting document", e)
                }
        }
    }

    override fun getAllFavoriteMovies(userId: String): Flow<List<Movie>> {

        val favMoviesRef = db.collection(Constants.LISTS)
            .document(Constants.FAVORITES)
            .collection(Constants.MOVIES)

        return favMoviesRef
            .whereEqualTo("userID", userId)
            .snapshots().map { querySnapshot -> querySnapshot.toObjects() }
    }

    override fun getAllToWatchMovies(userId: String): Flow<List<Movie>> {

        val toWatchMoviesRef = db.collection(Constants.LISTS)
            .document(Constants.TO_WATCH)
            .collection(Constants.MOVIES)

        return toWatchMoviesRef
            .whereEqualTo("userID", userId)
            .snapshots().map { querySnapshot -> querySnapshot.toObjects() }
    }

    override fun getAllFavoriteTv(userId: String): Flow<List<TvShow>> {

        val favTvRef = db.collection(Constants.LISTS)
            .document(Constants.FAVORITES)
            .collection(Constants.TV_SHOWS)

        return favTvRef
            .whereEqualTo("userID", userId)
            .snapshots().map { querySnapshot -> querySnapshot.toObjects() }
    }

    override fun getAllToWatchTv(userId: String): Flow<List<TvShow>> {

        val toWatchTvRef = db.collection(Constants.LISTS)
            .document(Constants.TO_WATCH)
            .collection(Constants.TV_SHOWS)

        return toWatchTvRef
            .whereEqualTo("userID", userId)
            .snapshots().map { querySnapshot -> querySnapshot.toObjects() }
    }

}