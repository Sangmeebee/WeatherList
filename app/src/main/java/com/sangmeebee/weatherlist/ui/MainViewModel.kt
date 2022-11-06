package com.sangmeebee.weatherlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.weatherlist.BuildConfig
import com.sangmeebee.weatherlist.domain.usecase.FetchWeatherUsecase
import com.sangmeebee.weatherlist.domain.usecase.GetCacheWeathersFlowUsecase
import com.sangmeebee.weatherlist.model.CHICAGO_ZIP
import com.sangmeebee.weatherlist.model.LONDON_ZIP
import com.sangmeebee.weatherlist.model.SEOUL_ZIP
import com.sangmeebee.weatherlist.model.WeatherListUiState
import com.sangmeebee.weatherlist.model.WeatherModel
import com.sangmeebee.weatherlist.model.ZipCode
import com.sangmeebee.weatherlist.model.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchWeatherUsecase: FetchWeatherUsecase,
    getCacheWeathersFlowUsecase: GetCacheWeathersFlowUsecase,
) : ViewModel() {

    private val zipcodes = listOf(ZipCode(SEOUL_ZIP), ZipCode(CHICAGO_ZIP), ZipCode(LONDON_ZIP))

    private val _uiState = MutableStateFlow(WeatherListUiState())
    val uiState = _uiState.asStateFlow()

    val weathers: StateFlow<Map<String, List<WeatherModel>>> = getCacheWeathersFlowUsecase().map { data ->
        val newMap = mutableMapOf<String, List<WeatherModel>>()
        data.forEach { (key, value) ->
            newMap[key] = value.toPresentation()
        }
        newMap
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyMap()
    )

    fun fetchWeather() = viewModelScope.launch {
        val results = fetchWeatherUsecase(zipcodes.map { it.code }, BuildConfig.OPEN_WEATHER_APP_ID)
        for (result in results) {
            result.onFailure { throwable -> fetchError(throwable) }
            if (result.isFailure) break
        }
        fetchLoading(isLoading = false)
    }

    fun fetchLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun fetchError(throwable: Throwable?) {
        _uiState.update { it.copy(error = throwable) }
    }
}
