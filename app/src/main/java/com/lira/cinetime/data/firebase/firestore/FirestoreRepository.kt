package com.lira.cinetime.data.firebase.firestore

import com.lira.cinetime.data.models.firebase.User

interface FirestoreRepository {

    suspend fun addUser(user: User)

}