package com.sangmeebee.weatherlist.model.mapper

import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.model.WeatherModel
import com.sangmeebee.weatherlist.model.WeatherViewType

internal fun Weather.toPresentation(timezone: String, convertTimestampToDate: (String, Long) -> String) = WeatherModel.WeatherContent(
    type = WeatherViewType.CONTENT,
    city = city,
    date = convertTimestampToDate(timezone, timestamp),
    tempMin = tempMin,
    tempMax = tempMax,
    iconName = iconName,
    iconType = iconType
)

internal fun List<Weather>.toPresentation(timezones: List<String>, convertTimestampToDate: (String, Long) -> String) = mapIndexed { index, weather ->
    weather.toPresentation(timezone = timezones[index], convertTimestampToDate = convertTimestampToDate)
}
