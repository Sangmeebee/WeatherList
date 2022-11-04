package com.sangmeebee.weatherlist.domain.model

data class Weather(
    val timestamp: Long,
    val tempMin: Double,
    val tempMax: Double,
    val iconName: String,
    val iconType: String,
)
