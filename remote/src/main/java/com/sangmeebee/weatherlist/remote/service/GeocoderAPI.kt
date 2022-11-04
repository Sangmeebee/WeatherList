package com.sangmeebee.weatherlist.remote.service

import com.sangmeebee.weatherlist.remote.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderAPI {
    @GET("geo/1.0/zip")
    suspend fun getLocation(
        @Query("zip") zipCode: String,
        @Query("appid") appId: String,
    ): LocationResponse
}
