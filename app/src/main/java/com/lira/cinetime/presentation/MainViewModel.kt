package com.lira.cinetime.presentation

import androidx.lifecycle.ViewModel
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.authFlow.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                    private val signOutUseCase: SignOutUseCase): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        checkIfConnected()
    }

    private fun checkIfConnected() {
        getCurrentUserUseCase() // maybe remove, just add a delay
        _isLoading.value = false
    }

    fun signOut(){
        signOutUseCase()
    }

}