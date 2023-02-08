package com.lira.cinetime.domain.authFlow

import com.google.firebase.auth.AuthResult
import com.lira.cinetime.data.firebase.ServiceAuthRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val authRepository: ServiceAuthRepository) {

    suspend operator fun invoke(email: String, password: String): Flow<AuthResult> {
        return authRepository.register(email, password)
    }

}