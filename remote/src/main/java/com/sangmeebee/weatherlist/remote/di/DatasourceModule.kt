package com.sangmeebee.weatherlist.remote.di

import com.sangmeebee.weatherlist.data.datasource.remote.GeocoderRemoteDatasource
import com.sangmeebee.weatherlist.data.datasource.remote.WeatherRemoteDatasource
import com.sangmeebee.weatherlist.remote.datasource.GeocoderRemoteDatasourceImpl
import com.sangmeebee.weatherlist.remote.datasource.WeatherRemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatasourceModule {

    @Singleton
    @Binds
    abstract fun bindGeocoderRemoteDatasource(impl: GeocoderRemoteDatasourceImpl): GeocoderRemoteDatasource

    @Singleton
    @Binds
    abstract fun bindWeatherRemoteDatasource(impl: WeatherRemoteDatasourceImpl): WeatherRemoteDatasource
}
