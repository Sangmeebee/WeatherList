package com.sangmeebee.weatherlist.data.model

data class WeatherEntity(
    val items: List<WeatherItemEntity>,
)

data class WeatherItemEntity(
    val timestamp: Long,
    val tempMin: Double,
    val tempMax: Double,
    val iconName: String,
    val iconType: String,
)
