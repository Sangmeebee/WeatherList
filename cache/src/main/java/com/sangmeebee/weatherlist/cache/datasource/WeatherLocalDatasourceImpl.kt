package com.sangmeebee.weatherlist.cache.datasource

import com.sangmeebee.weatherlist.cache.db.WeatherDao
import com.sangmeebee.weatherlist.cache.exceptions.DeleteCacheWeatherException
import com.sangmeebee.weatherlist.cache.exceptions.GetCacheWeatherException
import com.sangmeebee.weatherlist.cache.exceptions.PostCacheWeatherException
import com.sangmeebee.weatherlist.cache.model.mapper.toData
import com.sangmeebee.weatherlist.cache.model.mapper.toPref
import com.sangmeebee.weatherlist.data.datasource.local.WeatherLocalDatasource
import com.sangmeebee.weatherlist.data.di.IoDispatcher
import com.sangmeebee.weatherlist.data.model.WeatherEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class WeatherLocalDatasourceImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : WeatherLocalDatasource {
    override fun getWeathersFlow(): Flow<Map<String, List<WeatherEntity>>> = weatherDao.getWeathersFlow().map { data ->
        val newMap = mutableMapOf<String, List<WeatherEntity>>()
        data.forEach { (key, value) ->
            newMap[key] = value.toData()
        }
        newMap
    }

    override suspend fun getAllWeathers(): Result<Map<String, List<WeatherEntity>>> = try {
        withContext(ioDispatcher) {
            val newMap = mutableMapOf<String, List<WeatherEntity>>()
            weatherDao.getAllWeathers().forEach { (key, value) ->
                newMap[key] = value.toData()
            }
            Result.success(newMap)
        }
    } catch (e: Exception) {
        Result.failure(GetCacheWeatherException())
    }

    override suspend fun deleteWeathers(weathers: List<WeatherEntity>): Result<Unit> = try {
        withContext(ioDispatcher) {
            val result = weatherDao.deleteWeathers(weathers.toPref())
            Result.success(result)
        }
    } catch (e: Exception) {
        Result.failure(DeleteCacheWeatherException())
    }

    override suspend fun insertAll(weathers: List<WeatherEntity>): Result<Unit> = try {
        withContext(ioDispatcher) {
            val result = weatherDao.insertAll(weathers.toPref())
            Result.success(result)
        }
    } catch (e: Exception) {
        Result.failure(PostCacheWeatherException())
    }
}
