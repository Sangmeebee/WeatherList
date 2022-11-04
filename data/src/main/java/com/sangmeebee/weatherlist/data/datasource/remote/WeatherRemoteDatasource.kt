package com.sangmeebee.weatherlist.data.datasource.remote

import com.sangmeebee.weatherlist.data.model.WeatherEntity

interface WeatherRemoteDatasource {
    suspend fun getWeather(latitude: Double, longitude: Double, appId: String): Result<WeatherEntity>
}
