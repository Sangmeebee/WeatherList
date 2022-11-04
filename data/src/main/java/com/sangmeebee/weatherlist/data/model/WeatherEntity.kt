package com.sangmeebee.weatherlist.data.model

import com.sangmeebee.weatherlist.data.model.mapper.DataToDomainMapper
import com.sangmeebee.weatherlist.data.model.mapper.toDomain
import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.model.WeatherItem

data class WeatherEntity(
    val city: String,
    val items: List<WeatherItemEntity>,
) : DataToDomainMapper<Weather> {
    override fun toDomain(): Weather = Weather(
        city = city,
        items = items.toDomain()
    )
}

data class WeatherItemEntity(
    val timestamp: Long,
    val tempMin: Double,
    val tempMax: Double,
    val iconName: String,
    val iconType: String,
) : DataToDomainMapper<WeatherItem> {
    override fun toDomain(): WeatherItem = WeatherItem(
        timestamp = timestamp,
        tempMin = tempMin,
        tempMax = tempMax,
        iconName = iconName,
        iconType = iconType
    )
}
