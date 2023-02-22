package com.lira.cinetime.data.models.tvShows.tvDetails

import com.google.gson.annotations.SerializedName

data class FlatrateTv(
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("provider_id")
    val providerID: Long,
    @SerializedName("provider_name")
    val providerName: String,
    @SerializedName("display_priority")
    val displayPriority: Long
)
