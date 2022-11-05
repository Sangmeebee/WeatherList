package com.sangmeebee.weatherlist.data.datasource.local

import com.sangmeebee.weatherlist.data.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDatasource {
    fun getWeathersFlow(): Flow<Map<String, List<WeatherEntity>>>
    suspend fun getAllWeathers(): Result<Map<String, List<WeatherEntity>>>
    suspend fun deleteWeathers(weathers: List<WeatherEntity>): Result<Unit>
    suspend fun insertAll(weathers: List<WeatherEntity>): Result<Unit>
}
