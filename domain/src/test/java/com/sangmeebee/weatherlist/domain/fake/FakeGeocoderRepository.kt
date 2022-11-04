package com.sangmeebee.weatherlist.domain.fake

import com.sangmeebee.weatherlist.domain.model.Location
import com.sangmeebee.weatherlist.domain.repository.GeocoderRepository

class FakeGeocoderRepository : GeocoderRepository {
    override suspend fun getLocation(zipCode: String, appId: String): Result<Location> {
        if (zipCode.isEmpty()) {
            return Result.failure(IllegalArgumentException("invalid zipcode"))
        }
        if (appId.isEmpty()) {
            return Result.failure(IllegalArgumentException("invalid appid"))
        }
        return Result.success(Location(latitude = 37.5662, longitude = 126.9777))
    }
}

class ErrorFakeGeocoderRepository : GeocoderRepository {
    override suspend fun getLocation(zipCode: String, appId: String): Result<Location> {
        if (zipCode.isEmpty()) {
            return Result.failure(IllegalArgumentException("invalid zipcode"))
        }
        if (appId.isEmpty()) {
            return Result.failure(IllegalArgumentException("invalid appid"))
        }
        return Result.success(Location(latitude = 37.5662, longitude = 180.9777))
    }
}
