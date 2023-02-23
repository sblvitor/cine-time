package com.lira.cinetime.data.models.tvShows.tvDetails

import com.google.gson.annotations.SerializedName

data class CastTv(
    val id: Long,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val roles: List<Role>?,
    val totalEpisodeCount: Int
)
