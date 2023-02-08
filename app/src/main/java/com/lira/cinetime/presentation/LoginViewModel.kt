package com.lira.cinetime.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.lira.cinetime.domain.authFlow.LogInUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(private val logInUseCase: LogInUseCase) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun logIn(email: String, password: String){
        if(validateEmailPassword(email, password)) {
            viewModelScope.launch {
                logInUseCase(email, password)
                    .onStart {
                        _state.postValue(State.Loading)
                    }.catch {
                        _state.postValue(State.Error(it))
                    }.collect {
                        _state.postValue(State.Success(it.user))
                    }
            }
        }
    }

    private fun validateEmailPassword(email: String, password: String): Boolean{
        return !(email.isEmpty() || password.isEmpty())
    }

    sealed class State {
        object Loading: State()
        data class Success(val user: FirebaseUser?): State()
        data class Error(val error: Throwable): State()
    }
}