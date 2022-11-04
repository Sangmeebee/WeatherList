package com.sangmeebee.weatherlist.domain.fake

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import kotlin.math.absoluteValue

class FakeWeatherRepository : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double, appId: String): Result<Weather> {
        if (latitude.absoluteValue > MAX_LATITUDE_VALUE || longitude.absoluteValue > MAX_LONGITUDE_VALUE) {
            return Result.failure(IllegalArgumentException("invalid location"))
        }
        return Result.success(Weather(city = "Seoul", items = emptyList()))
    }

    companion object {
        private const val MAX_LATITUDE_VALUE = 90
        private const val MAX_LONGITUDE_VALUE = 180
    }
}
