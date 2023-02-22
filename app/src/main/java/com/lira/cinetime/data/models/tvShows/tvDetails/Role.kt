package com.lira.cinetime.data.models.tvShows.tvDetails

import com.google.gson.annotations.SerializedName

data class Role(
    val character: String,
    @SerializedName("episode_count")
    val episodeCount: Int
)
