package com.sangmeebee.weatherlist.domain.model

data class Weather(
    val city: String,
    val items: List<WeatherItem>,
)

data class WeatherItem(
    val timestamp: Long,
    val tempMin: Double,
    val tempMax: Double,
    val iconName: String,
    val iconType: String,
)
