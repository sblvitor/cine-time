package com.lira.cinetime.domain.authFlow

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository
import com.lira.cinetime.data.models.firebase.User

class AddUserToDBUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(user: User) {
        firestoreRepository.addUser(user)
    }

}