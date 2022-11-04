package com.sangmeebee.weatherlist.remote.model

import com.google.gson.annotations.SerializedName

internal data class WeatherResponse(
    @SerializedName("list")
    val items: List<WeatherItemResponse>,
)

internal data class WeatherItemResponse(
    @SerializedName("dt")
    val timestamp: Long,
    @SerializedName("main")
    val tempResponse: TempResponse,
    @SerializedName("weather")
    val weatherIcon: List<WeatherIconResponse>,
)

internal data class TempResponse(
    @SerializedName("temp_min")
    val min: Double,
    @SerializedName("temp_max")
    val max: Double,
)

internal data class WeatherIconResponse(
    @SerializedName("main")
    val iconName: String,
    @SerializedName("icon")
    val iconType: String,
)
