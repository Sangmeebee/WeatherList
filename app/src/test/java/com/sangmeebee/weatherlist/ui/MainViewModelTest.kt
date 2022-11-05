package com.sangmeebee.weatherlist.ui

import com.sangmeebee.weatherlist.domain.usecase.FetchWeatherUsecase
import com.sangmeebee.weatherlist.domain.usecase.GetCacheWeathersFlowUsecase
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
        mainViewModel = MainViewModel(fetchWeatherUsecase, getCacheWeathersFlowUsecase)
    }

    @Test
    fun `날씨 정보를 불러온다`() = runTest {
        // given
        // when
        // then
    }

    @Test
    fun `날씨 정보를 불러오는데 실패하면 오류를 뷰에 전파한다`() {
        // given
        // when
        // then
    }

    @Test
    fun `뷰의 로딩상태를 업데이트한다`() {
        // given
        // when
        // then
    }

    @Test
    fun `뷰의 에러상태를 업데이트한다`() {
        // given
        // when
        // then
    }
}
