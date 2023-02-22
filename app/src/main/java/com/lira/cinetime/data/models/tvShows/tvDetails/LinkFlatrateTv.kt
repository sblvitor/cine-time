package com.lira.cinetime.data.models.tvShows.tvDetails

import com.google.gson.annotations.SerializedName

data class LinkFlatrateTv(
    val link: String,
    @SerializedName("flatrate")
    val flatRate: List<FlatrateTv>?
)
