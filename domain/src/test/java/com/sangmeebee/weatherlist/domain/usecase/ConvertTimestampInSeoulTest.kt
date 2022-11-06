package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.model.Weather
import org.junit.Test

class ConvertTimestampInSeoulTest {

    @Test
    fun `날씨 정보의 unix timpstamp를 서울기준으로 변경한다`() {
        // given
        val convertTimestampInSeoul = ConvertTimestampInSeoul()
        val weathers = listOf(
            Weather(
                city = "Chicago", timestamp = 1668045600, tempMin = 14.08, tempMax = 21.32, iconName = "Clouds", iconType = "04d"
            ),
            Weather(
                city = "London", timestamp = 1667786400, tempMin = 11.26, tempMax = 14.89, iconName = "Rain", iconType = "10d"
            )
        )
        val expected = listOf(
            Weather(
                city = "Chicago", timestamp = 1667991600, tempMin = 14.08, tempMax = 21.32, iconName = "Clouds", iconType = "04d"
            ),
            Weather(
                city = "London", timestamp = 1667754000, tempMin = 11.26, tempMax = 14.89, iconName = "Rain", iconType = "10d"
            )
        )
        // when
        val actual = convertTimestampInSeoul(weathers)
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
