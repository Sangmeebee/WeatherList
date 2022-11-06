package com.sangmeebee.weatherlist.model

sealed class WeatherModel(open val type: WeatherViewType) {

    data class WeatherTitle(
        override val type: WeatherViewType,
        val city: String,
    ) : WeatherModel(type)

    data class WeatherContent(
        override val type: WeatherViewType,
        val city: String,
        val timestamp: Long,
        val tempMin: Double,
        val tempMax: Double,
        val iconName: String,
        val iconType: String,
    ) : WeatherModel(type)
}

enum class WeatherViewType {
    TITLE, CONTENT
}
