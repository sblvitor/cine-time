package com.lira.cinetime.data.models.tvShows.tvDetails

import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_count")
    val episodeCount: Int,
    val id: Long,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("season_number")
    val seasonNumber: Int
)
