package com.sangmeebee.weatherlist.domain.usecase

import org.junit.Before
import org.junit.Test

class GetRemoveWeatherUsecaseTest {

    private lateinit var getRemoveWeatherUsecase: GetRemoveWeatherUsecase

    @Before
    fun setUp() {
        getRemoveWeatherUsecase = GetRemoveWeatherUsecase()
    }

    @Test
    fun `날씨 정보가 오늘 이전의 데이터라면 반환한다`() {
        // given
        // when
        // then
    }

    @Test
    fun `날씨 정보가 최대 출력 개수를 초과하면 반환한다`() {
        // given
        // when
        // then
    }
}
