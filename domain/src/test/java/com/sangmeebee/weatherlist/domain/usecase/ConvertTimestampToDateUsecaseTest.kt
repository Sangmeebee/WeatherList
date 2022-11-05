package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ConvertTimestampToDateUsecaseTest {

    @Test
    fun `Unix timestamp를 날짜 문자열로 반환한다`() {
        // given
        val convertTimestampToDateUsecase = ConvertTimestampToDateUsecase()
        val timestamp = 4088618200
        val expected = "2099-07-25"
        // when
        val actual = convertTimestampToDateUsecase(timestamp)
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
