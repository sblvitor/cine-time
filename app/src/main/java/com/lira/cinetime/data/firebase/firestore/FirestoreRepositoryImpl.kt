package com.lira.cinetime.data.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.lira.cinetime.core.Constants
import com.lira.cinetime.data.models.firebase.User

class FirestoreRepositoryImpl(private val db: FirebaseFirestore): FirestoreRepository {

    override suspend fun addUser(user: User) {
        db.collection(Constants.USERS)
            .document(user.id)
            .set(user)
            .addOnCompleteListener {
                Log.d("Register", "addUser: DocumentSnapshot successfully written!")
            }
            .addOnFailureListener {
                Log.d("Register", "addUser: Error adding document", it)
            }
    }

}