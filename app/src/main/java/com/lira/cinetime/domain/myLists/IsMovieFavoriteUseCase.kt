package com.lira.cinetime.domain.myLists

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class IsMovieFavoriteUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(movieId: Long, userId: String): Boolean {
        return firestoreRepository.isThisMovieFavorite(movieId, userId)
    }

}