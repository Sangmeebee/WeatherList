package com.sangmeebee.weatherlist.cache.datasource

import com.sangmeebee.weatherlist.cache.db.WeatherDao
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

    override suspend fun getAllWeathers(): Result<Map<String, List<WeatherEntity>>> = runCatching {
        withContext(ioDispatcher) {
            val newMap = mutableMapOf<String, List<WeatherEntity>>()
            weatherDao.getAllWeathers().forEach { (key, value) ->
                newMap[key] = value.toData()
            }
            newMap
        }
    }

    override suspend fun deleteAll(weathers: List<WeatherEntity>): Result<Unit> = runCatching {
        withContext(ioDispatcher) {
            weatherDao.deleteAll(weathers.toPref())
        }
    }


    override suspend fun insertAll(weathers: List<WeatherEntity>): Result<Unit> = runCatching {
        withContext(ioDispatcher) {
            weatherDao.insertAll(weathers.toPref())
        }
    }
}
