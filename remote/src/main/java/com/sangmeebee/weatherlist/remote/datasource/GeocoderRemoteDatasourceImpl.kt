package com.sangmeebee.weatherlist.remote.datasource

import com.sangmeebee.weatherlist.data.datasource.remote.GeocoderRemoteDatasource
import com.sangmeebee.weatherlist.data.di.IoDispatcher
import com.sangmeebee.weatherlist.data.model.LocationEntity
import com.sangmeebee.weatherlist.remote.service.GeocoderAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GeocoderRemoteDatasourceImpl @Inject constructor(
    private val geocoderAPI: GeocoderAPI,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : GeocoderRemoteDatasource {
    override suspend fun getLocation(zipCode: String, appId: String): Result<LocationEntity> = runCatching {
        withContext(ioDispatcher) {
            geocoderAPI.getLocation(zipCode, appId).toData()
        }
    }
}
