package com.sangmeebee.weatherlist.domain.model

data class Weather(
    val city: String,
    val timestamp: Long,
    val tempMin: Double,
    val tempMax: Double,
    val iconName: String,
    val iconType: String,
)
