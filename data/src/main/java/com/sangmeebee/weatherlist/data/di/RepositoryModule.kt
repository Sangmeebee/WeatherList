package com.sangmeebee.weatherlist.data.di

import com.sangmeebee.weatherlist.data.repository.GeocoderRepositoryImpl
import com.sangmeebee.weatherlist.data.repository.WeatherRepositoryImpl
import com.sangmeebee.weatherlist.domain.repository.GeocoderRepository
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGeocoderRepository(impl: GeocoderRepositoryImpl): GeocoderRepository

    @Singleton
    @Binds
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository
}
