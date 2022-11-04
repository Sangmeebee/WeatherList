package com.sangmeebee.weatherlist.remote.datasource

import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.data.di.IoDispatcher
import com.sangmeebee.weatherlist.data.model.WeatherEntity
import com.sangmeebee.weatherlist.remote.model.mapper.toData
import com.sangmeebee.weatherlist.remote.service.WeatherAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class WeatherRemoteDatasourceImpl @Inject constructor(
    private val weatherAPI: WeatherAPI,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : WeatherRemoteDatasource {
    override suspend fun getWeather(latitude: Double, longitude: Double, appId: String): Result<List<WeatherEntity>> = runCatching {
        withContext(ioDispatcher) {
            weatherAPI.getWeather(latitude, longitude, appId).items.toData()
        }
    }
}
