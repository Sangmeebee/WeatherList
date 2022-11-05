package com.sangmeebee.weatherlist.domain.usecase

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ConvertTimestampToDateUsecase @Inject constructor() {
    operator fun invoke(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
        return dateFormat.format(date)
    }

    companion object {
        const val DATE_FORMAT_PATTERN = "yyyy-MM-dd"
    }
}
