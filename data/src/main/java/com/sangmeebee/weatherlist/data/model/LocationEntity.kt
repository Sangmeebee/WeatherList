package com.sangmeebee.weatherlist.data.model

import com.sangmeebee.weatherlist.data.model.mapper.DataToDomainMapper
import com.sangmeebee.weatherlist.domain.model.Location

data class LocationEntity(
    val latitude: Double,
    val longitude: Double,
) : DataToDomainMapper<Location> {
    override fun toDomain(): Location = Location(
        latitude = latitude,
        longitude = longitude
    )
}
