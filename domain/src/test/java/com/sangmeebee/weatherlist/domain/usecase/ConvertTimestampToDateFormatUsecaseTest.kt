package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ConvertTimestampToDateFormatUsecaseTest {

    private lateinit var convertTimestampToDateFormatUsecase: ConvertTimestampToDateFormatUsecase

    @Before
    fun setUp() {
        convertTimestampToDateFormatUsecase = ConvertTimestampToDateFormatUsecase()
    }

    @Test
    fun `오늘이면 Today를 반환한다`() {
        // when
        val actual = convertTimestampToDateFormatUsecase(timezone = "Asia/Seoul", timestamp = System.currentTimeMillis() / 1000)
        // then
        assertThat(actual).isEqualTo("Today")
    }

    @Test
    fun `내일이면 Tomorrow를 반환한다`() {
        // when
        val actual = convertTimestampToDateFormatUsecase(timezone = "Asia/Seoul", timestamp = System.currentTimeMillis() / 1000 + 86400)
        // then
        assertThat(actual).isEqualTo("Tomorrow")
    }

    @Test
    fun `오늘도 내일도 아니라면 DateFormat 형태로 반환한다`() {
        // when
        val actual = convertTimestampToDateFormatUsecase(timezone = "Asia/Seoul", timestamp = 1667559600)
        // then
        assertThat(actual).isEqualTo("Fri 4 Nov")
    }
}
