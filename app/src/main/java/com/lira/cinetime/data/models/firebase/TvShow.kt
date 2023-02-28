package com.lira.cinetime.data.models.firebase

data class TvShow(
    val userID: String,
    val tvId: Long,
    val name: String,
    val posterPath: String? = null
)
