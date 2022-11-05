package com.sangmeebee.weatherlist.cache.di

import android.content.Context
import androidx.room.Room
import com.sangmeebee.weatherlist.cache.db.AppDatabase
import com.sangmeebee.weatherlist.cache.db.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DBModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "WeatherList.db")
        .build()

    @Singleton
    @Provides
    fun provideWeatherDao(database: AppDatabase): WeatherDao = database.weatherDao()
}
