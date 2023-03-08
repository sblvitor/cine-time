package com.lira.cinetime.domain.account

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository
import com.lira.cinetime.data.models.firebase.User
import kotlinx.coroutines.flow.Flow

class GetUserFirestoreUseCase(private val firestoreRepository: FirestoreRepository) {

    operator fun invoke(userId: String): Flow<User> {
        return firestoreRepository.getUser(userId)
    }

}