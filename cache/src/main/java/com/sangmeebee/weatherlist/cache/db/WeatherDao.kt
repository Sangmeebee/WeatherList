package com.sangmeebee.weatherlist.cache.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sangmeebee.weatherlist.cache.model.WeatherPref
import kotlinx.coroutines.flow.Flow

@Dao
internal interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weathers: List<WeatherPref>)

    @MapInfo(keyColumn = "city")
    @Query("SELECT weather.city AS city, * FROM weather ORDER BY timestamp ASC")
    fun getWeathersFlow(): Flow<Map<String, List<WeatherPref>>>

    @MapInfo(keyColumn = "city")
    @Query("SELECT weather.city AS city, * FROM weather ORDER BY timestamp ASC")
    suspend fun getAllWeathers(): Map<String, List<WeatherPref>>

    @Delete
    suspend fun deleteWeathers(weathers: List<WeatherPref>)
}
