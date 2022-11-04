package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDatasource: WeatherRemoteDatasource,
) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double, appId: String): Result<Weather> =
        weatherRemoteDatasource.getWeather(latitude, longitude, appId).mapCatching { it.toDomain() }
}
