package com.sangmeebee.weatherlist.remote.model

import com.google.gson.annotations.SerializedName

internal data class WeatherResponse(
    @SerializedName("timezone")
    val city: String,
    @SerializedName("daily")
    val items: List<WeatherItemResponse>,
)

internal data class WeatherItemResponse(
    @SerializedName("dt")
    val timestamp: Long,
    @SerializedName("temp")
    val tempResponse: TempResponse,
    @SerializedName("weather")
    val weatherIcon: List<WeatherIconResponse>,
)

internal data class TempResponse(
    val min: Double,
    val max: Double,
)

internal data class WeatherIconResponse(
    @SerializedName("main")
    val iconName: String,
    @SerializedName("icon")
    val iconType: String,
)
