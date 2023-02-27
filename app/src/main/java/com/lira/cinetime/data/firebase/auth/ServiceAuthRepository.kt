package com.lira.cinetime.data.firebase.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface ServiceAuthRepository {

    suspend fun getCurrentUser(): Flow<FirebaseUser?>

    suspend fun logIn(email: String, password: String): Flow<AuthResult>

    suspend fun register(email: String, password: String): Flow<AuthResult>

    fun signOut()

}