package com.lira.cinetime.domain.account

import android.net.Uri
import com.lira.cinetime.data.firebase.storage.StorageRepository
import com.lira.cinetime.data.models.firebase.UploadResponse

class UploadUserImgUseCase(private val storageRepository: StorageRepository) {

    suspend operator fun invoke(selectedImageUri: Uri, ext: String?): UploadResponse {
        return storageRepository.uploadUserImage(selectedImageUri, ext)
    }

}