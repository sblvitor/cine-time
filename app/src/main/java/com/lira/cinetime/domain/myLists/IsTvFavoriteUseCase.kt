package com.lira.cinetime.domain.myLists

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class IsTvFavoriteUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(tvId: Long, userId: String): Boolean {
        return firestoreRepository.isThisTvFavorite(tvId, userId)
    }

}