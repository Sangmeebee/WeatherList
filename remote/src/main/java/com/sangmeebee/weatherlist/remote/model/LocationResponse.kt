package com.sangmeebee.weatherlist.remote.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
)
