package com.sangmeebee.weatherlist.data.datasource.remote

import com.sangmeebee.weatherlist.data.model.LocationEntity

interface GeocoderRemoteDatasource {
    suspend fun getLocation(zipCode: String, appId: String): Result<LocationEntity>
}
