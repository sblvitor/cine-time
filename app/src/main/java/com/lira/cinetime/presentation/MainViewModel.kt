package com.lira.cinetime.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lira.cinetime.data.preferences.SettingsRepository
import com.lira.cinetime.data.preferences.UiMode
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.authFlow.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                    private val signOutUseCase: SignOutUseCase,
                    private val settingsRepository: SettingsRepository): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _currentThemeMode = MutableLiveData<UiMode>()
    val currentThemeMode: LiveData<UiMode> = _currentThemeMode

    init {
        checkIfConnected()

        viewModelScope.launch {
            settingsRepository.uiModeFlow.collect {
                _currentThemeMode.value = it
            }
        }
    }

    private fun checkIfConnected() {
        getCurrentUserUseCase() // maybe remove, just add a delay
        _isLoading.value = false
    }

    fun signOut(){
        signOutUseCase()
    }

}