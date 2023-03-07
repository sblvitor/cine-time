package com.lira.cinetime.data.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ServiceAuthRepositoryImpl(private val auth: FirebaseAuth): ServiceAuthRepository {

    override fun getCurrentUser() = flow {
        emit(auth.currentUser)
    }

    override fun logIn(email: String, password: String) = flow {
        emit(auth.signInWithEmailAndPassword(email, password).await())
    }

    override fun register(email: String, password: String) = flow {
        emit(auth.createUserWithEmailAndPassword(email, password).await())
    }

    override fun signOut() {
        auth.signOut()
    }
}