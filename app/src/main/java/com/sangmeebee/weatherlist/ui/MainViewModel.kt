package com.sangmeebee.weatherlist.ui

import androidx.lifecycle.ViewModel
import com.sangmeebee.weatherlist.domain.usecase.FetchWeatherUsecase
import com.sangmeebee.weatherlist.domain.usecase.GetCacheWeathersFlowUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchWeatherUsecase: FetchWeatherUsecase,
    getCacheWeathersFlowUsecase: GetCacheWeathersFlowUsecase,
) : ViewModel()
