package com.sangmeebee.weatherlist.data.model

import com.sangmeebee.weatherlist.data.model.mapper.DataToDomainMapper
import com.sangmeebee.weatherlist.domain.model.Weather

data class WeatherEntity(
    val city: String,
    val timestamp: Long,
    val tempMin: Double,
    val tempMax: Double,
    val iconName: String,
    val iconType: String,
) : DataToDomainMapper<Weather> {
    override fun toDomain(): Weather = Weather(
        city = city,
        timestamp = timestamp,
        tempMin = tempMin,
        tempMax = tempMax,
        iconName = iconName,
        iconType = iconType
    )
}
