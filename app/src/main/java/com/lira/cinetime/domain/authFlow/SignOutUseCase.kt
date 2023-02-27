package com.lira.cinetime.domain.authFlow

import com.lira.cinetime.data.firebase.auth.ServiceAuthRepository

class SignOutUseCase(private val authRepository: ServiceAuthRepository) {

    operator fun invoke() {
        authRepository.signOut()
    }

}