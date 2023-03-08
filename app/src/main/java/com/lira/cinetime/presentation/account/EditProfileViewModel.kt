package com.lira.cinetime.presentation.account

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lira.cinetime.domain.account.UpUserImgAndNameUseCase
import com.lira.cinetime.domain.account.UpdateUserNameUseCase
import com.lira.cinetime.domain.account.UpdateUserProfileImgUseCase
import com.lira.cinetime.domain.account.UploadUserImgUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(private val uploadUserImgUseCase: UploadUserImgUseCase,
                           private val updateUserProfileImgUseCase: UpdateUserProfileImgUseCase,
                           private val upUserImgAndNameUseCase: UpUserImgAndNameUseCase,
                           private val updateUserNameUseCase: UpdateUserNameUseCase) : ViewModel() {

    private val _update = MutableStateFlow<State>(State.Loading)
    val update = _update.asStateFlow()

    fun updateUserProfileImg(userId: String, selectedImage: Uri, imgExt: String?) {
        viewModelScope.launch {
            val uploadResponse = uploadUserImgUseCase(selectedImage, imgExt)
            uploadResponse.imgUrl?.let { imgUrl ->
                runCatching {
                    updateUserProfileImgUseCase(userId, imgUrl)
                }.onSuccess {
                    _update.value = State.Success
                }.onFailure {
                    _update.value = State.Error(it)
                }
            }
            uploadResponse.exception?.let {
                _update.value = State.Error(it)
            }
        }
    }

    fun updateUserProfileImgAndName(userId: String, selectedImage: Uri, imgExt: String?, name: String) {
        viewModelScope.launch {
            val uploadResponse = uploadUserImgUseCase(selectedImage, imgExt)
            uploadResponse.imgUrl?.let { imgUrl ->
                runCatching {
                    upUserImgAndNameUseCase(userId, imgUrl, name)
                }.onSuccess {
                    _update.value = State.Success
                }.onFailure {
                    _update.value = State.Error(it)
                }
            }
            uploadResponse.exception?.let {
                _update.value = State.Error(it)
            }
        }
    }

    fun updateUserName(userId: String, name: String) {
        viewModelScope.launch {
            runCatching {
                updateUserNameUseCase(userId, name)
            }.onSuccess {
                _update.value = State.Success
            }.onFailure {
                _update.value = State.Error(it)
            }
        }
    }

    sealed class State {
        object Loading: State()
        object Success: State()
        data class Error(val error: Throwable): State()
    }
}