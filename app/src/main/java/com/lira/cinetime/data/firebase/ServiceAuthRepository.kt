package com.lira.cinetime.data.firebase

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface ServiceAuthRepository {

    suspend fun isConnected(): Flow<Boolean>

    suspend fun logIn(email: String, password: String): Flow<AuthResult>

    suspend fun register(email: String, password: String): Flow<AuthResult>

    fun signOut()

}