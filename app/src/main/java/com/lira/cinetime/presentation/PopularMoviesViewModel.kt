package com.lira.cinetime.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lira.cinetime.domain.authFlow.IsConnectedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PopularMoviesViewModel(private val isConnectedUseCase: IsConnectedUseCase): ViewModel() {

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    init {
        checkIfConnected()
    }

    private fun checkIfConnected() {
        viewModelScope.launch {
            isConnectedUseCase().collect {
                    _isConnected.value = it
                }
        }
    }

}