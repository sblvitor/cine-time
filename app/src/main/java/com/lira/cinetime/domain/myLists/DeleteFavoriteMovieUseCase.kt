package com.lira.cinetime.domain.myLists

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class DeleteFavoriteMovieUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(movieId: Long, userId: String) {
        firestoreRepository.deleteFavoriteMovie(movieId, userId)
    }

}