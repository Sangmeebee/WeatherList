package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.fake.FakeWeatherRepository
import com.sangmeebee.weatherlist.domain.model.Weather
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PostCacheWeatherUsecaseTest {

    private lateinit var postCacheWeatherUsecase: PostCacheWeatherUsecase
    private lateinit var convertTimestampInSeoul: ConvertTimestampInSeoul

    @Before
    fun setUp() {
        convertTimestampInSeoul = spyk(ConvertTimestampInSeoul())
        postCacheWeatherUsecase = PostCacheWeatherUsecase(FakeWeatherRepository(), convertTimestampInSeoul)
    }

    @Test
    fun `날씨 정보의 unix timpstamp를 서울기준으로 변경한다`() = runTest {
        // when
        postCacheWeatherUsecase(emptyList())
        // then
        verify { convertTimestampInSeoul(emptyList()) }
    }

    @Test
    fun `날씨 리스트를 저장한다`() = runTest {
        // given
        val weathers = listOf(
            Weather(
                city = "Seoul",
                timestamp = 4088618200, // 2099-7-25
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            )
        )
        // when
        val actual = postCacheWeatherUsecase(weathers)
        // then
        assertThat(actual.isSuccess).isTrue()
    }
}
