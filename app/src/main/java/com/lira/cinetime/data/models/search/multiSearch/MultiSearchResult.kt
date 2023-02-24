package com.lira.cinetime.data.models.search.multiSearch

import com.google.gson.annotations.SerializedName

data class MultiSearchResult(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Long,
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("profile_path")
    val profilePath: String?
)
