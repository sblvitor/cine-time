package com.lira.cinetime.domain.myLists

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class IsTvInToWatchUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(tvId: Long, userId: String): Boolean {
        return firestoreRepository.isThisTvInToWatch(tvId, userId)
    }

}