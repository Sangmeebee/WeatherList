package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.local.WeatherLocalDatasource
import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.data.model.mapper.toData
import com.sangmeebee.weatherlist.data.model.mapper.toDomain
import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDatasource: WeatherRemoteDatasource,
    private val weatherLocalDatasource: WeatherLocalDatasource,
) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double, appId: String): Result<List<Weather>> =
        weatherRemoteDatasource.getWeather(latitude, longitude, appId).mapCatching { it.toDomain() }

    override fun getCacheWeathersFlow(): Flow<Map<String, List<Weather>>> = weatherLocalDatasource.getWeathersFlow().map { data ->
        val newMap = mutableMapOf<String, List<Weather>>()
        data.forEach { (key, value) ->
            newMap[key] = value.toDomain()
        }
        newMap
    }

    override suspend fun getAllWeathersAtCache(): Result<Map<String, List<Weather>>> {
        val newMap = mutableMapOf<String, List<Weather>>()
        return weatherLocalDatasource.getAllWeathers().mapCatching {
            it.forEach { (key, value) ->
                newMap[key] = value.toDomain()
            }
            newMap
        }
    }

    override suspend fun deleteWeathersAtCache(weathers: List<Weather>): Result<Unit> =
        weatherLocalDatasource.deleteWeathers(weathers.toData())

    override suspend fun insertAllAtCache(weathers: List<Weather>): Result<Unit> =
        weatherLocalDatasource.insertAll(weathers.toData())
}
