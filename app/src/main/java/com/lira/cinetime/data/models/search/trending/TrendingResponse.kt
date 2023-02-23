package com.lira.cinetime.data.models.search.trending

import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    val page: Int,
    val results: List<TrendingResult>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
