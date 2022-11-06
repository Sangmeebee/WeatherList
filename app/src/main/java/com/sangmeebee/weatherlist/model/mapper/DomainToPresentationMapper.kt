package com.sangmeebee.weatherlist.model.mapper

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.model.WeatherModel
import com.sangmeebee.weatherlist.model.WeatherViewType

internal fun Weather.toPresentation() = WeatherModel.WeatherContent(
    type = WeatherViewType.CONTENT,
    city = city,
    timestamp = timestamp,
    tempMin = tempMin,
    tempMax = tempMax,
    iconName = iconName,
    iconType = iconType
)

internal fun List<Weather>.toPresentation() = map { it.toPresentation() }
