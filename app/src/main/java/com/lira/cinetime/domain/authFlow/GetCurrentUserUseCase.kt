package com.lira.cinetime.domain.authFlow

import com.google.firebase.auth.FirebaseUser
import com.lira.cinetime.data.firebase.auth.ServiceAuthRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase(private val authRepository: ServiceAuthRepository) {

    suspend operator fun invoke(): Flow<FirebaseUser?> {
        return authRepository.getCurrentUser()
    }

}