package com.sangmeebee.weatherlist.model

data class WeatherListUiState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
)
