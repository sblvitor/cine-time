package com.lira.cinetime.domain.account

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository

class UpdateUserProfileImgUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend operator fun invoke(userId: String, profileImg: String){
        firestoreRepository.updateUserProfileImage(userId, profileImg)
    }

}