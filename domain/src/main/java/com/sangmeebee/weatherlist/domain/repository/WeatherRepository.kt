package com.sangmeebee.weatherlist.domain.repository

import com.sangmeebee.weatherlist.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double, appId: String): Result<List<Weather>>

    fun getCacheWeathersFlow(): Flow<Map<String, List<Weather>>>
    suspend fun getAllWeathersAtCache(): Result<Map<String, List<Weather>>>
    suspend fun deleteWeathersAtCache(weathers: List<Weather>): Result<Unit>
    suspend fun insertAllAtCache(weathers: List<Weather>): Result<Unit>
}
