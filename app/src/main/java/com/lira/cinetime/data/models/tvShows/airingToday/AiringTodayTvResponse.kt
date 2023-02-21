package com.lira.cinetime.data.models.tvShows.airingToday

import com.google.gson.annotations.SerializedName

data class AiringTodayTvResponse(
    val page: Int,
    val results: List<AiringTodayTvResult>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
