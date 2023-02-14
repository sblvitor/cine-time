package com.lira.cinetime.data.models.topRated

import com.google.gson.annotations.SerializedName

data class TopRatedResponse(
    val page: Int,
    val results: List<TopRatedResult>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
