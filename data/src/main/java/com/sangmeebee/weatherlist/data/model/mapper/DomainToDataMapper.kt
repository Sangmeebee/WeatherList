package com.sangmeebee.weatherlist.data.model.mapper

import com.sangmeebee.weatherlist.data.model.WeatherEntity
import com.sangmeebee.weatherlist.domain.model.Weather

internal fun Weather.toData() = WeatherEntity(
    city = city,
    timestamp = timestamp,
    tempMin = tempMin,
    tempMax = tempMax,
    iconName = iconName,
    iconType = iconType
)

internal fun List<Weather>.toData() = map { it.toData() }
