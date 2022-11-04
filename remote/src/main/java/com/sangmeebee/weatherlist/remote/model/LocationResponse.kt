package com.sangmeebee.weatherlist.remote.model

import com.google.gson.annotations.SerializedName
import com.sangmeebee.weatherlist.data.model.LocationEntity
import com.sangmeebee.weatherlist.remote.model.mapper.RemoteToDataMapper

internal data class LocationResponse(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
) : RemoteToDataMapper<LocationEntity> {
    override fun toData(): LocationEntity = LocationEntity(
        latitude = latitude,
        longitude = longitude
    )
}
