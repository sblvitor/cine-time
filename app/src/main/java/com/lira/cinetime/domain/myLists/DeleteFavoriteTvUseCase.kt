package com.lira.cinetime.domain.myLists

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class DeleteFavoriteTvUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(tvId: Long, userId: String) {
        firestoreRepository.deleteFavoriteTv(tvId, userId)
    }

}