package com.lira.cinetime.data.models.search.multiSearch

import com.google.gson.annotations.SerializedName

data class MultiSearchResponse(
    val page: Int,
    val results: List<MultiSearchResult>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
