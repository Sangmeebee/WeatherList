package com.sangmeebee.weatherlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.weatherlist.BuildConfig
import com.sangmeebee.weatherlist.domain.usecase.ConvertTimestampToDateFormatUsecase
import com.sangmeebee.weatherlist.domain.usecase.FetchWeatherUsecase
import com.sangmeebee.weatherlist.domain.usecase.GetCacheWeathersFlowUsecase
import com.sangmeebee.weatherlist.model.CHICAGO_ZIP
import com.sangmeebee.weatherlist.model.LONDON_ZIP
import com.sangmeebee.weatherlist.model.SEOUL_ZIP
import com.sangmeebee.weatherlist.model.WeatherListUiState
import com.sangmeebee.weatherlist.model.WeatherModel
import com.sangmeebee.weatherlist.model.WeatherViewType
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
    private val convertTimestampToDateFormatUsecase: ConvertTimestampToDateFormatUsecase,
) : ViewModel() {

    private val zipcodes = listOf(ZipCode(SEOUL_ZIP), ZipCode(LONDON_ZIP), ZipCode(CHICAGO_ZIP))

    private val _uiState = MutableStateFlow(WeatherListUiState())
    val uiState = _uiState.asStateFlow()

    val weathers: StateFlow<List<WeatherModel>> = getCacheWeathersFlowUsecase().map { data ->
        val weatherModel = mutableListOf<WeatherModel>()
        data.toSortedMap(compareBy<String> { it.split("/").last() }.reversed())
            .forEach { (key, weathers) ->
                weatherModel.add(WeatherModel.WeatherTitle(type = WeatherViewType.TITLE, city = key.split("/").last()))
                weatherModel.addAll(
                    weathers.toPresentation(
                        timezones = weathers.map { it.city },
                        convertTimestampToDate = convertTimestampToDateFormatUsecase::invoke
                    )
                )
            }
        weatherModel
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
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
