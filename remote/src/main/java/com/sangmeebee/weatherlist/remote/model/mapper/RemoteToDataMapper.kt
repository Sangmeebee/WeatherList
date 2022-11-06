package com.sangmeebee.weatherlist.remote.model.mapper

import com.sangmeebee.weatherlist.data.model.WeatherEntity
import com.sangmeebee.weatherlist.remote.model.WeatherResponse

internal interface RemoteToDataMapper<T> {
    fun toData(): T
}

internal fun WeatherResponse.toData(): List<WeatherEntity> =
    items.map { response ->
        WeatherEntity(
            city = city,
            timestamp = response.timestamp,
            tempMin = response.tempResponse.min,
            tempMax = response.tempResponse.max,
            iconName = response.weatherIcon.first().iconName,
            iconType = response.weatherIcon.first().iconType
        )
    }
