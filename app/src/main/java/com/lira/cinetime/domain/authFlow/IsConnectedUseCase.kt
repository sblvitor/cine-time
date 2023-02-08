package com.lira.cinetime.domain.authFlow

import com.lira.cinetime.data.firebase.ServiceAuthRepository
import kotlinx.coroutines.flow.Flow

class IsConnectedUseCase(private val authRepository: ServiceAuthRepository) {

    suspend operator fun invoke(): Flow<Boolean> {
        return authRepository.isConnected()
    }

}