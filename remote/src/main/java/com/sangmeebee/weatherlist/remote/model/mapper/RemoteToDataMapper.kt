package com.sangmeebee.weatherlist.remote.model.mapper

import com.sangmeebee.weatherlist.data.model.WeatherEntity
import com.sangmeebee.weatherlist.remote.model.WeatherResponse

internal interface RemoteToDataMapper<T> {
    fun toData(): T
}

internal fun <T> List<RemoteToDataMapper<T>>.toData(): List<T> = map { it.toData() }

internal fun WeatherResponse.toData(): List<WeatherEntity> =
    items.map { response ->
        WeatherEntity(
            city = city.split("/").last(),
            timestamp = response.timestamp,
            tempMin = response.tempResponse.min,
            tempMax = response.tempResponse.max,
            iconName = response.weatherIcon.first().iconName,
            iconType = response.weatherIcon.first().iconType
        )
    }
