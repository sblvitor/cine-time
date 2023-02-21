package com.lira.cinetime.data.models.tvShows.popularTv

import com.google.gson.annotations.SerializedName

data class PopularTvResponse(
    val page: Int,
    val results: List<PopularTvResult>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
