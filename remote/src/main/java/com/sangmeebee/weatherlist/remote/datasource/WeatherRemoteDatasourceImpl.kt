package com.sangmeebee.weatherlist.remote.datasource

import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.data.di.IoDispatcher
import com.sangmeebee.weatherlist.data.model.WeatherEntity
import com.sangmeebee.weatherlist.remote.exceptions.DisConnectNetworkException
import com.sangmeebee.weatherlist.remote.exceptions.EMPTY_RESULT_LOCATION
import com.sangmeebee.weatherlist.remote.exceptions.EmptyResultLocationException
import com.sangmeebee.weatherlist.remote.exceptions.ILLEGAL_APP_TOKEN
import com.sangmeebee.weatherlist.remote.exceptions.ILLEGAL_LOCATION
import com.sangmeebee.weatherlist.remote.exceptions.IllegalAppTokenException
import com.sangmeebee.weatherlist.remote.exceptions.IllegalLocationException
import com.sangmeebee.weatherlist.remote.model.mapper.toData
import com.sangmeebee.weatherlist.remote.service.WeatherAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class WeatherRemoteDatasourceImpl @Inject constructor(
    private val weatherAPI: WeatherAPI,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : WeatherRemoteDatasource {
    override suspend fun getWeather(latitude: Double, longitude: Double, appId: String): Result<List<WeatherEntity>> = try {
        withContext(ioDispatcher) {
            Result.success(weatherAPI.getWeather(latitude, longitude, appId).toData())
        }
    } catch (e: Exception) {
        when {
            e.message?.contains(ILLEGAL_LOCATION) == true -> Result.failure(IllegalLocationException())
            e.message?.contains(EMPTY_RESULT_LOCATION) == true -> Result.failure(EmptyResultLocationException())
            e.message?.contains(ILLEGAL_APP_TOKEN) == true -> Result.failure(IllegalAppTokenException())
            else -> Result.failure(DisConnectNetworkException())
        }
    }
}
