package com.lira.cinetime.domain.myLists

import com.google.firebase.firestore.DocumentReference
import com.lira.cinetime.data.firebase.firestore.FirestoreRepository
import com.lira.cinetime.data.models.firebase.TvShow
import kotlinx.coroutines.flow.Flow

class AddTvToFavoritesUseCase(private val firestoreRepository: FirestoreRepository) {

    operator fun invoke(tvShow: TvShow): Flow<DocumentReference> {
        return firestoreRepository.addTvToFavorites(tvShow)
    }

}