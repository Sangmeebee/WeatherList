package com.sangmeebee.weatherlist.remote.model

import com.google.gson.annotations.SerializedName
import com.sangmeebee.weatherlist.data.model.WeatherEntity
import com.sangmeebee.weatherlist.data.model.WeatherItemEntity
import com.sangmeebee.weatherlist.remote.model.mapper.RemoteToDataMapper

internal data class WeatherResponse(
    @SerializedName("list")
    val items: List<WeatherItemResponse>,
) : RemoteToDataMapper<WeatherEntity> {
    override fun toData(): WeatherEntity = WeatherEntity(
        items = items.map { it.toData() }
    )
}

internal data class WeatherItemResponse(
    @SerializedName("dt")
    val timestamp: Long,
    @SerializedName("main")
    val tempResponse: TempResponse,
    @SerializedName("weather")
    val weatherIcon: List<WeatherIconResponse>,
) : RemoteToDataMapper<WeatherItemEntity> {
    override fun toData(): WeatherItemEntity = WeatherItemEntity(
        timestamp = timestamp,
        tempMin = tempResponse.min,
        tempMax = tempResponse.max,
        iconName = weatherIcon.first().iconName,
        iconType = weatherIcon.first().iconType
    )
}

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
