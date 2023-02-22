package com.lira.cinetime.data.models.tvShows.topRated

import com.google.gson.annotations.SerializedName

data class TopRatedTvResponse(
    val page: Int,
    val results: List<TopRatedTvResult>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
