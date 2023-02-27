package com.lira.cinetime.domain.myLists

import com.google.firebase.firestore.DocumentReference
import com.lira.cinetime.data.firebase.firestore.FirestoreRepository
import com.lira.cinetime.data.models.firebase.Movie
import kotlinx.coroutines.flow.Flow

class AddMovieToFavoritesUseCase(private val firestoreRepository: FirestoreRepository) {

    operator fun invoke(movie: Movie): Flow<DocumentReference> {
        return firestoreRepository.addMovieToFavorites(movie)
    }

}