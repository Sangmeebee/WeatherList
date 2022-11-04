package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.remote.GeocoderRemoteDatasource
import com.sangmeebee.weatherlist.domain.model.Location
import com.sangmeebee.weatherlist.domain.repository.GeocoderRepository
import javax.inject.Inject

internal class GeocoderRepositoryImpl @Inject constructor(
    private val geocoderRemoteDatasource: GeocoderRemoteDatasource,
) : GeocoderRepository {
    override suspend fun getLocation(zipCode: String, appId: String): Result<Location> =
        geocoderRemoteDatasource.getLocation(zipCode, appId).mapCatching { it.toDomain() }
}
