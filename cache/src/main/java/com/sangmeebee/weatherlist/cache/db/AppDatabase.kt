package com.sangmeebee.weatherlist.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sangmeebee.weatherlist.cache.model.WeatherPref

@Database(
    entities = [WeatherPref::class],
    version = 1
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
