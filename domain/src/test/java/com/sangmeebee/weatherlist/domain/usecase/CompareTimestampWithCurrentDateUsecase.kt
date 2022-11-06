package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CompareTimestampWithCurrentDateUsecaseTest {

    @Test
    fun `오늘 이후면 False를 반환한다`() {
        // given
        val compareTimestampWithCurrentDateUsecase = CompareTimestampWithCurrentDateUsecase()
        val timestamp = 4088618200L // 2099-07-25
        val timeZone = "Asia/Seoul"
        // when
        val actual = compareTimestampWithCurrentDateUsecase(timeZone, timestamp)
        // then
        assertThat(actual).isFalse()
    }

    @Test
    fun `오늘 이전이면 True를 반환한다`() {
        // given
        val compareTimestampWithCurrentDateUsecase = CompareTimestampWithCurrentDateUsecase()
        val timestamp = 1667734464L // 2022-11-6
        val timeZone = "Asia/Seoul"
        // when
        val actual = compareTimestampWithCurrentDateUsecase(timeZone, timestamp)
        // then
        assertThat(actual).isTrue()
    }
}
