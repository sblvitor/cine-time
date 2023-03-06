package com.lira.cinetime.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.lira.cinetime.data.models.firebase.User
import com.lira.cinetime.data.preferences.SettingsRepository
import com.lira.cinetime.data.preferences.UiMode
import com.lira.cinetime.domain.account.GetUserFirestoreUseCase
import com.lira.cinetime.domain.authFlow.GetCurrentUserUseCase
import com.lira.cinetime.domain.authFlow.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(getCurrentUserUseCase: GetCurrentUserUseCase,
                       private val getUserFirestoreUseCase: GetUserFirestoreUseCase,
                       private val settingsRepository: SettingsRepository,
                       private val signOutUseCase: SignOutUseCase) : ViewModel() {

    private val _fireUser =  MutableStateFlow<State>(State.Loading)
    val fireUser = _fireUser.asStateFlow()

    private val _currentThemeMode = MutableLiveData<UiMode>()
    val currentThemeMode: LiveData<UiMode> = _currentThemeMode

    var user: FirebaseUser? = null

    init {
        user = getCurrentUserUseCase()
        user?.let { getUser(it.uid) }

        viewModelScope.launch {
            settingsRepository.uiModeFlow.collect {
                _currentThemeMode.value = it
            }
        }
    }

    private fun getUser(userId: String) {
        viewModelScope.launch {
            try {
                val result = getUserFirestoreUseCase(userId)
                if(result != null) {
                    val resultUser = result.toObject(User::class.java)
                    _fireUser.value = State.Success(resultUser!!)
                }
            } catch (e: Exception) {
                _fireUser.value = State.Error(e)
            }
        }
    }

    fun onThemeModeSwitched(uiMode: UiMode) {
        viewModelScope.launch {
            settingsRepository.setUiMode(uiMode)
        }
    }

    fun signOut() {
        signOutUseCase()
    }

    sealed class State {
        object Loading: State()
        data class Success(val user: User): State()
        data class Error(val error: Throwable): State()
    }

}