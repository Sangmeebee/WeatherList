package com.sangmeebee.weatherlist.domain.repository

import com.sangmeebee.weatherlist.domain.model.Location

interface GeocoderRepository {
    suspend fun getLocation(zipCode: String, appId: String): Result<Location>
}
