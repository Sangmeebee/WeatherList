package com.sangmeebee.weatherlist.remote.model

import com.google.gson.annotations.SerializedName
import com.sangmeebee.weatherlist.data.model.WeatherEntity
import com.sangmeebee.weatherlist.data.model.WeatherItemEntity
import com.sangmeebee.weatherlist.remote.model.mapper.RemoteToDataMapper
import com.sangmeebee.weatherlist.remote.model.mapper.toData

internal data class WeatherResponse(
    @SerializedName("timezone")
    val city: String,
    @SerializedName("daily")
    val items: List<WeatherItemResponse>,
) : RemoteToDataMapper<WeatherEntity> {
    override fun toData(): WeatherEntity = WeatherEntity(
        city = city.split("/").last(),
        items = items.toData()
    )
}

internal data class WeatherItemResponse(
    @SerializedName("dt")
    val timestamp: Long,
    @SerializedName("temp")
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
    val min: Double,
    val max: Double,
)

internal data class WeatherIconResponse(
    @SerializedName("main")
    val iconName: String,
    @SerializedName("icon")
    val iconType: String,
)
