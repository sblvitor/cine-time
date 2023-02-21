package com.lira.cinetime.data.models.movies.nowPlaying

import com.google.gson.annotations.SerializedName

data class NowPlayingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<NowPlayingResult>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long
)
