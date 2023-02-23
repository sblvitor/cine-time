package com.lira.cinetime.data.models.search.trending

import com.google.gson.annotations.SerializedName

data class TrendingResult(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Long,
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?
)