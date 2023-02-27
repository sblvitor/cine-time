package com.lira.cinetime.domain.myLists

import com.google.firebase.firestore.QuerySnapshot
import com.lira.cinetime.data.firebase.firestore.FirestoreRepository
import kotlinx.coroutines.flow.Flow

class IsMovieFavoriteUseCase(private val firestoreRepository: FirestoreRepository) {

    operator fun invoke(movieId: Long, userId: String): Flow<QuerySnapshot> {
        return firestoreRepository.isThisMovieFavorite(movieId, userId)
    }

}