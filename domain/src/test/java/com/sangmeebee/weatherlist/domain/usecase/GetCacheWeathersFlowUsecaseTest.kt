package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.fake.FakeWeatherRepository
import com.sangmeebee.weatherlist.domain.model.Weather
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCacheWeathersFlowUsecaseTest {
    @Test
    fun `현재 로컬 데이터베이스에 저장된 데이터를 스트림 형태로 반환한다`() = runTest {
        // given
        val getCacheWeathersFlowUsecase = GetCacheWeathersFlowUsecase(FakeWeatherRepository())
        val expected = mapOf(
            "Seoul" to listOf(
                Weather(
                    city = "Seoul",
                    timestamp = 1667617200,
                    tempMin = -2.34,
                    tempMax = 11.63,
                    iconName = "Clear",
                    iconType = "01d"
                )
            )
        )
        // when
        val actual = getCacheWeathersFlowUsecase()
        // then
        assertThat(actual.first()).isEqualTo(expected)
    }
}
