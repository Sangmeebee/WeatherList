package com.sangmeebee.weatherlist.model

data class WeatherModel(
    val city: String,
    val timestamp: Long,
    val tempMin: Double,
    val tempMax: Double,
    val iconName: String,
    val iconType: String,
)
