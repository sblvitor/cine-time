package com.lira.cinetime.data.models.movieDetails

import com.google.gson.annotations.SerializedName

data class Cast(
    val id: Long,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val character: String?
)
