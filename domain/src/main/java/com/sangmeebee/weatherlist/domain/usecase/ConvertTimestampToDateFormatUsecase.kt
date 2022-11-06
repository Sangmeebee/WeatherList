package com.sangmeebee.weatherlist.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class ConvertTimestampToDateFormatUsecase @Inject constructor() {
    operator fun invoke(timezone: String, timestamp: Long): String {
        val dateFormat = SimpleDateFormat("EEE d MMM", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone(timezone)

        val calendar = Calendar.getInstance()
        val currentDate = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrowDate = dateFormat.format(calendar.time)

        val comparedDate = dateFormat.format(Date(timestamp * 1000))
        if (comparedDate == currentDate) {
            return TODAY
        }
        if (comparedDate == tomorrowDate) {
            return TOMORROW
        }
        return comparedDate
    }

    companion object {
        private const val TODAY = "Today"
        private const val TOMORROW = "Tomorrow"
    }
}
