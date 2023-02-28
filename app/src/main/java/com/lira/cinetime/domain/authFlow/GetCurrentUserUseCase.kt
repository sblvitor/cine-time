package com.lira.cinetime.domain.authFlow

import com.google.firebase.auth.FirebaseUser
import com.lira.cinetime.data.firebase.auth.ServiceAuthRepository

class GetCurrentUserUseCase(private val authRepository: ServiceAuthRepository) {

    operator fun invoke(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

}