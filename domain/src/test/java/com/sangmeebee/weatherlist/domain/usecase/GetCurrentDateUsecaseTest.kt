package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GetCurrentDateUsecaseTest {
    @Test
    fun `현재 날짜를 문자열로 반환한다`() {
        // given
        val getCurrentDateUsecase = GetCurrentDateUsecase()
        val actual = getCurrentDateUsecase()
        // then
        assertThat(actual).isInstanceOf(String::class.java)
    }
}
