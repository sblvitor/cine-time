package com.lira.cinetime.data.models.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val profileImage: String? = null
): Parcelable
