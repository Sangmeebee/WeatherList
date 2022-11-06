package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.model.Weather
import org.junit.Before
import org.junit.Test

class GetRemoveWeatherUsecaseTest {

    private lateinit var getRemoveWeatherUsecase: GetRemoveWeatherUsecase

    @Before
    fun setUp() {
        getRemoveWeatherUsecase = GetRemoveWeatherUsecase(CompareTimestampWithCurrentDateUsecase())
    }

    @Test
    fun `날씨 정보가 오늘 이전의 데이터라면 반환한다`() {
        // given
        val weathers = listOf(
            Weather(
                city = "Seoul",
                timestamp = 1667617200,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            )
        )
        // when
        val actual = getRemoveWeatherUsecase(weathers)
        // then
        assertThat(actual).isEqualTo(weathers)
    }

    @Test
    fun `날씨 정보가 오늘 이후의 데이터라면 반환되지 않는다`() {
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
        val actual = getRemoveWeatherUsecase(weathers)
        // then
        assertThat(actual).isEmpty()
    }

    @Test
    fun `날씨 정보가 최대 출력 개수를 초과하면 반환한다`() {
        // given
        val weathers = listOf(
            Weather(
                city = "Seoul",
                timestamp = 4088618200,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            ),
            Weather(
                city = "Seoul",
                timestamp = 4088618201,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            ),
            Weather(
                city = "Seoul",
                timestamp = 4088618202,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            ),
            Weather(
                city = "Seoul",
                timestamp = 4088618203,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            ),
            Weather(
                city = "Seoul",
                timestamp = 4088618204,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            ),
            Weather(
                city = "Seoul",
                timestamp = 4088618205,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            ),
            Weather(
                city = "Seoul",
                timestamp = 4088618206,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            )
        )
        // when
        val actual = getRemoveWeatherUsecase(weathers)
        // then
        val expected = listOf(
            Weather(
                city = "Seoul",
                timestamp = 4088618206,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            )
        )
        assertThat(actual).isEqualTo(expected)
    }
}
