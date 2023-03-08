package com.lira.cinetime.domain.account

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class UpdateUserNameUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(userId: String, name: String) {
        firestoreRepository.updateUserName(userId, name)
    }

}