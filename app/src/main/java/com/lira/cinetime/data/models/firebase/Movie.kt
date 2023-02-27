package com.lira.cinetime.data.models.firebase

data class Movie(
    val userID: String,
    val movieId: Long,
    val title: String,
    val posterPath: String? = null
)
