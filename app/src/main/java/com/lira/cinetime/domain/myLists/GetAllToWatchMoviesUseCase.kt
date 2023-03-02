package com.lira.cinetime.domain.myLists

import com.lira.cinetime.data.firebase.firestore.FirestoreRepository
import com.lira.cinetime.data.models.firebase.Movie
import kotlinx.coroutines.flow.Flow

class GetAllToWatchMoviesUseCase(private val firestoreRepository: FirestoreRepository) {

    operator fun invoke(userId: String): Flow<List<Movie>> {
        return firestoreRepository.getAllToWatchMovies(userId)
    }

}