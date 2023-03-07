package com.lira.cinetime.data.firebase.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface ServiceAuthRepository {

    fun getCurrentUser(): Flow<FirebaseUser?>

    fun logIn(email: String, password: String): Flow<AuthResult>

    fun register(email: String, password: String): Flow<AuthResult>

    fun signOut()

}