package com.lira.cinetime.data.models

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    val page: Int,
    val results: List<PopularMoviesResult>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
