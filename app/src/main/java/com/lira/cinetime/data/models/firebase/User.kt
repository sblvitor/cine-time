package com.lira.cinetime.data.models.firebase

data class User(
    val id: String,
    val name: String? = null,
    val email: String? = null,
    val profileImage: String? = null
)
