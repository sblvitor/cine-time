package com.lira.cinetime.domain.account

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class UpUserImgAndNameUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(userId: String, profileImg: String, name: String) {
        firestoreRepository.updateUserProfileImgAndName(userId, profileImg, name)
    }

}