package com.sangmeebee.weatherlist.remote.di

import com.sangmeebee.weatherlist.remote.service.GeocoderAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object APIModule {

    @Singleton
    @Provides
    fun provideGeocoderAPI(retrofit: Retrofit): GeocoderAPI =
        retrofit.create(GeocoderAPI::class.java)
}
