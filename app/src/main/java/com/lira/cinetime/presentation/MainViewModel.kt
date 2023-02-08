package com.lira.cinetime.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lira.cinetime.domain.authFlow.IsConnectedUseCase
import com.lira.cinetime.domain.authFlow.SignOutUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val isConnectedUseCase: IsConnectedUseCase,
                    private val signOutUseCase: SignOutUseCase): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        checkIfConnected()
    }

    private fun checkIfConnected() {
        viewModelScope.launch {
            isConnectedUseCase()
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