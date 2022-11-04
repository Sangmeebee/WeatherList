package com.sangmeebee.weatherlist.domain.repository

import com.sangmeebee.weatherlist.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double, appId: String): Result<Weather>
}
