package com.sangmeebee.weatherlist.ui

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.weatherlist.BuildConfig
import com.sangmeebee.weatherlist.cache.exceptions.DeleteCacheWeatherException
import com.sangmeebee.weatherlist.cache.exceptions.PostCacheWeatherException
import com.sangmeebee.weatherlist.domain.usecase.ConvertTimestampToDateFormatUsecase
import com.sangmeebee.weatherlist.domain.usecase.FetchWeatherUsecase
import com.sangmeebee.weatherlist.domain.usecase.GetCacheWeathersFlowUsecase
import com.sangmeebee.weatherlist.model.CHICAGO_ZIP
import com.sangmeebee.weatherlist.model.LONDON_ZIP
import com.sangmeebee.weatherlist.model.SEOUL_ZIP
import com.sangmeebee.weatherlist.model.ZipCode
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fetchWeatherUsecase: FetchWeatherUsecase
    private lateinit var getCacheWeathersFlowUsecase: GetCacheWeathersFlowUsecase
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        fetchWeatherUsecase = mockk(relaxed = true)
        getCacheWeathersFlowUsecase = mockk(relaxed = true)
        mainViewModel = MainViewModel(fetchWeatherUsecase, getCacheWeathersFlowUsecase, ConvertTimestampToDateFormatUsecase())
    }

    @Test
    fun `날씨 정보를 불러온다`() = runTest {
        // given
        val zipcodes = listOf(ZipCode(SEOUL_ZIP), ZipCode(LONDON_ZIP), ZipCode(CHICAGO_ZIP))
        // when
        mainViewModel.fetchWeather()
        // then
        coVerify { fetchWeatherUsecase(zipcodes.map { it.code }, BuildConfig.OPEN_WEATHER_APP_ID) }
    }

    @Test
    fun `날씨 정보를 불러오는데 실패하면 오류를 뷰에 전파한다`() = runTest {
        // given
        val zipcodes = listOf(ZipCode(SEOUL_ZIP), ZipCode(LONDON_ZIP), ZipCode(CHICAGO_ZIP))
        coEvery { fetchWeatherUsecase(zipcodes.map { it.code }, BuildConfig.OPEN_WEATHER_APP_ID) } returns listOf(
            Result.success(Unit),
            Result.failure(PostCacheWeatherException()),
            Result.success(Unit)
        )
        // when
        mainViewModel.fetchWeather()
        // then
        val actual = mainViewModel.uiState.value.error
        assertThat(actual).isInstanceOf(PostCacheWeatherException::class.java)
    }

    @Test
    fun `뷰의 로딩상태를 업데이트한다`() {
        // when
        mainViewModel.fetchLoading(true)
        // then
        val actual = mainViewModel.uiState.value.isLoading
        assertThat(actual).isTrue()
    }

    @Test
    fun `뷰의 에러상태를 업데이트한다`() {
        // when
        mainViewModel.fetchError(DeleteCacheWeatherException())
        // then
        val actual = mainViewModel.uiState.value.error
        assertThat(actual).isInstanceOf(DeleteCacheWeatherException::class.java)
    }
}
