package com.sangmeebee.weatherlist.model.mapper

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.model.WeatherModel

internal fun Weather.toPresentation() = WeatherModel(
    city = city,
    timestamp = timestamp,
    tempMin = tempMin,
    tempMax = tempMax,
    iconName = iconName,
    iconType = iconType
)

internal fun List<Weather>.toPresentation() = map { it.toPresentation() }
