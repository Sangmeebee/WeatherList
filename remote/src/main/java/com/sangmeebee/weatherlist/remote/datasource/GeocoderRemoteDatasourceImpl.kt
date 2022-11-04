package com.sangmeebee.weatherlist.remote.datasource

import com.sangmeebee.weatherlist.data.datasource.remote.GeocoderRemoteDatasource
import com.sangmeebee.weatherlist.data.di.IoDispatcher
import com.sangmeebee.weatherlist.data.model.LocationEntity
import com.sangmeebee.weatherlist.remote.exceptions.DisConnectNetworkException
import com.sangmeebee.weatherlist.remote.exceptions.EMPTY_RESULT_LOCATION
import com.sangmeebee.weatherlist.remote.exceptions.EmptyResultLocationException
import com.sangmeebee.weatherlist.remote.exceptions.ILLEGAL_APP_TOKEN
import com.sangmeebee.weatherlist.remote.exceptions.ILLEGAL_LOCATION
import com.sangmeebee.weatherlist.remote.exceptions.IllegalAppTokenException
import com.sangmeebee.weatherlist.remote.exceptions.IllegalLocationException
import com.sangmeebee.weatherlist.remote.service.GeocoderAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GeocoderRemoteDatasourceImpl @Inject constructor(
    private val geocoderAPI: GeocoderAPI,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : GeocoderRemoteDatasource {
    override suspend fun getLocation(zipCode: String, appId: String): Result<LocationEntity> = try {
        withContext(ioDispatcher) {
            Result.success(geocoderAPI.getLocation(zipCode, appId).toData())
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
