package com.sangmeebee.weatherlist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.domain.model.Weather
import com.sangmeebee.weatherlist.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteCacheWeatherUsecaseTest {

    private lateinit var deleteCacheWeatherUsecase: DeleteCacheWeatherUsecase
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getRemoveWeatherUsecase: GetRemoveWeatherUsecase

    @Before
    fun setUp() {
        weatherRepository = mockk(relaxed = true)
        getRemoveWeatherUsecase = mockk()
        deleteCacheWeatherUsecase = spyk(DeleteCacheWeatherUsecase(weatherRepository, getRemoveWeatherUsecase))
    }

    @Test
    fun `로컬 데이터베이스에 저장된 날씨 정보를 불러온다`() = runTest {
        // given
        coEvery { deleteCacheWeatherUsecase() } returns Result.success(Unit)
        // when
        deleteCacheWeatherUsecase()
        // then
        coVerify { weatherRepository.getAllWeathersAtCache() }
    }

    @Test
    fun `로컬 데이터베이스에 저장된 날씨 정보를 불러오는 것을 실패하면 예외를 반환한다`() = runTest {
        // given
        coEvery { weatherRepository.getAllWeathersAtCache() } returns Result.failure(Throwable())
        // when
        val actual = deleteCacheWeatherUsecase()
        // then
        assertThat(actual.isFailure).isTrue()
    }

    @Test
    fun `받아온 날씨 정보 중 오늘 이전의 데이터거나 최대 출력개수를 초과한 데이터가 있다면 그 정보들을 삭제한다`() = runTest {
        // given
        val removedWeathers = listOf(
            Weather(
                city = "Seoul",
                timestamp = 1667617200,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            )
        )
        coEvery { weatherRepository.getAllWeathersAtCache() } returns Result.success(mapOf("Seoul" to removedWeathers))
        coEvery { getRemoveWeatherUsecase(removedWeathers) } returns removedWeathers
        // when
        deleteCacheWeatherUsecase()
        // then
        coVerify { weatherRepository.deleteWeathersAtCache(removedWeathers) }
    }

    @Test
    fun `정보를 삭제하는데 실패하면 예외를 반환한다`() = runTest {
        // given
        val removedWeathers = listOf(
            Weather(
                city = "Seoul",
                timestamp = 1667617200,
                tempMin = -2.34,
                tempMax = 11.63,
                iconName = "Clear",
                iconType = "01d"
            )
        )
        coEvery { weatherRepository.getAllWeathersAtCache() } returns Result.success(mapOf("Seoul" to removedWeathers))
        coEvery { getRemoveWeatherUsecase(removedWeathers) } returns removedWeathers
        coEvery { weatherRepository.deleteWeathersAtCache(removedWeathers) } returns Result.failure(Throwable())
        // when
        val actual = deleteCacheWeatherUsecase()
        // then
        assertThat(actual.isFailure).isTrue()
    }
}
