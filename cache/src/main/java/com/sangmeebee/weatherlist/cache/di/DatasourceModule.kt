package com.sangmeebee.weatherlist.cache.di

import com.sangmeebee.weatherlist.cache.datasource.WeatherLocalDatasourceImpl
import com.sangmeebee.weatherlist.data.datasource.local.WeatherLocalDatasource
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
    abstract fun bindWeatherLocalDatasource(impl: WeatherLocalDatasourceImpl): WeatherLocalDatasource
}
