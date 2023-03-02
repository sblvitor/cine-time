package com.lira.cinetime.domain.myLists

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository
import com.lira.cinetime.data.models.firebase.TvShow
import kotlinx.coroutines.flow.Flow

class GetAllToWatchTvUseCase(private val firestoreRepository: FirestoreRepository) {

    operator fun invoke(userId: String): Flow<List<TvShow>> {
        return firestoreRepository.getAllToWatchTv(userId)
    }

}