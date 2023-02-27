package com.lira.cinetime.data.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ServiceAuthRepositoryImpl(private val auth: FirebaseAuth): ServiceAuthRepository {

    override suspend fun getCurrentUser() = flow {
        val currentUser = auth.currentUser
        emit(currentUser)
    }

    override suspend fun logIn(email: String, password: String) = flow {
        emit(auth.signInWithEmailAndPassword(email, password).await())
    }

    override suspend  fun register(email: String, password: String) = flow {
        emit(auth.createUserWithEmailAndPassword(email, password).await())
    }

    override fun signOut() {
        auth.signOut()
    }
}