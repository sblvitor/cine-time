package com.lira.cinetime.domain.account

import com.google.firebase.firestore.DocumentSnapshot
import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class GetUserFirestoreUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(userId: String): DocumentSnapshot? {
        return firestoreRepository.getUser(userId)
    }

}