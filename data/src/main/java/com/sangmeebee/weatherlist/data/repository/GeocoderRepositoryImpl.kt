package com.sangmeebee.weatherlist.data.repository

import com.sangmeebee.weatherlist.data.datasource.remote.GeocoderRemoteDatasource
import com.sangmeebee.weatherlist.domain.repository.GeocoderRepository
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(
    private val geocoderRemoteDatasource: GeocoderRemoteDatasource,
) : GeocoderRepository
