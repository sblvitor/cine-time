package com.lira.cinetime.domain.authFlow

import com.google.firebase.auth.AuthResult
import com.lira.cinetime.data.firebase.auth.ServiceAuthRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val authRepository: ServiceAuthRepository) {

    operator fun invoke(email: String, password: String): Flow<AuthResult> {
        return authRepository.register(email, password)
    }

}