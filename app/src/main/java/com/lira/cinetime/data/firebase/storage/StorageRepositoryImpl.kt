package com.lira.cinetime.data.firebase.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.lira.cinetime.data.models.firebase.UploadResponse
import kotlinx.coroutines.tasks.await

class StorageRepositoryImpl(private val storage: FirebaseStorage): StorageRepository {

    override suspend fun uploadUserImage(selectedImageUri: Uri, ext: String?): UploadResponse {
        val response = UploadResponse()

        val storageRef = storage.reference
        val userImageRef = storageRef.child(selectedImageUri.hashCode().toString() + "." + ext)

        try {
            response.imgUrl = userImageRef.putFile(selectedImageUri).await().metadata?.reference?.downloadUrl?.await().toString()
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

}