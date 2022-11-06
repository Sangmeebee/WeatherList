package com.sangmeebee.weatherlist.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class CompareTimestampWithCurrentDateUsecase @Inject constructor() {
    operator fun invoke(timezone: String, timestamp: Long): Boolean {
        val date = Date(timestamp * 1000)
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone(timezone)

        return dateFormat.format(date) < dateFormat.format(currentDate)
    }

    companion object {
        const val DATE_FORMAT_PATTERN = "yyyy-MM-dd"
    }
}
