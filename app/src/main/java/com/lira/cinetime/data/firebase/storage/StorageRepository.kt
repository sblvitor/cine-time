package com.lira.cinetime.data.firebase.storage

import android.net.Uri
import com.lira.cinetime.data.models.firebase.UploadResponse

interface StorageRepository {

    suspend fun uploadUserImage(selectedImageUri: Uri, ext: String?): UploadResponse

}