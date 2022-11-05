package com.sangmeebee.weatherlist.cache.model.mapper

import com.sangmeebee.weatherlist.cache.model.WeatherPref
import com.sangmeebee.weatherlist.data.model.WeatherEntity

internal fun WeatherEntity.toPref() = WeatherPref(
    city = city,
    timestamp = timestamp,
    tempMin = tempMin,
    tempMax = tempMax,
    iconName = iconName,
    iconType = iconType
)

internal fun List<WeatherEntity>.toPref() = map { it.toPref() }
