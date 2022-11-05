package com.sangmeebee.weatherlist.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.sangmeebee.weatherlist.cache.model.mapper.CacheToDataMapper
import com.sangmeebee.weatherlist.data.model.WeatherEntity

@Entity(tableName = "weather", primaryKeys = ["city", "timestamp"])
data class WeatherPref(
    val city: String,
    val timestamp: Long,
    @ColumnInfo(name = "temp_min")
    val tempMin: Double,
    @ColumnInfo(name = "temp_max")
    val tempMax: Double,
    @ColumnInfo(name = "icon_name")
    val iconName: String,
    @ColumnInfo(name = "icon_type")
    val iconType: String,
) : CacheToDataMapper<WeatherEntity> {
    override fun toData(): WeatherEntity = WeatherEntity(
        city = city,
        timestamp = timestamp,
        tempMin = tempMin,
        tempMax = tempMax,
        iconName = iconName,
        iconType = iconType
    )
}
