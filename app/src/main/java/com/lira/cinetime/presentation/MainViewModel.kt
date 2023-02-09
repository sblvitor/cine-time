package com.lira.cinetime.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.authFlow.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                    private val signOutUseCase: SignOutUseCase): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        checkIfConnected()
    }

    private fun checkIfConnected() {
        viewModelScope.launch {
            getCurrentUserUseCase()
                .onStart {
                    _isLoading.value = true
                } .collect {
                    _isLoading.value = false
                }
        }
    }

    fun signOut(){
        signOutUseCase()
    }

}