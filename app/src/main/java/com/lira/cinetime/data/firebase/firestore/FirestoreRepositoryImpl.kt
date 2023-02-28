package com.lira.cinetime.data.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.lira.cinetime.core.Constants
import com.lira.cinetime.data.models.firebase.Movie
import com.lira.cinetime.data.models.firebase.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirestoreRepositoryImpl(private val db: FirebaseFirestore): FirestoreRepository {

    override suspend fun addUser(user: User) {
        db.collection(Constants.USERS)
            .document(user.id)
            .set(user)
            .addOnCompleteListener {
                Log.d("Register", "addUser: DocumentSnapshot successfully written!")
            }
            .addOnFailureListener {
                Log.w("Register", "addUser: Error adding document", it)
            }
    }

    override fun addMovieToFavorites(movie: Movie) = flow {
        emit(db.collection(Constants.LISTS)
            .document(Constants.FAVORITES)
            .collection(Constants.MOVIES)
            .add(movie)
            .await())
    }

    override fun isThisMovieFavorite(movieId: Long, userId: String) = flow {
        emit(db.collection(Constants.LISTS)
            .document(Constants.FAVORITES)
            .collection(Constants.MOVIES)
            .whereEqualTo("movieId", movieId)
            .whereEqualTo("userID", userId)
            .get().await())
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

    override fun isThisMovieInToWatch(movieId: Long, userId: String) = flow {
        emit(db.collection(Constants.LISTS)
            .document(Constants.TO_WATCH)
            .collection(Constants.MOVIES)
            .whereEqualTo("movieId", movieId)
            .whereEqualTo("userID", userId)
            .get().await())
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

}