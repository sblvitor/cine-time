package com.lira.cinetime.presentation.movies

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviesViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase) : ViewModel() {

    private val _isConnected = MutableStateFlow<FirebaseUser?>(null)
    val isConnected = _isConnected.asStateFlow()

    init {
        checkIfConnected()
    }

    private fun checkIfConnected() {
        _isConnected.value = getCurrentUserUseCase()
    }

}