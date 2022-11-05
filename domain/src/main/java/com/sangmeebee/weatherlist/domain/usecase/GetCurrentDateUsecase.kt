package com.sangmeebee.weatherlist.domain.usecase

import com.sangmeebee.weatherlist.domain.usecase.ConvertTimestampToDateUsecase.Companion.DATE_FORMAT_PATTERN
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GetCurrentDateUsecase {
    operator fun invoke(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}
