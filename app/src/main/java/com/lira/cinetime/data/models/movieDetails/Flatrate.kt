package com.lira.cinetime.data.models.movieDetails

import com.google.gson.annotations.SerializedName

data class Flatrate(
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("provider_id")
    val providerID: Long,
    @SerializedName("provider_name")
    val providerName: String,
    @SerializedName("display_priority")
    val displayPriority: Long
)
