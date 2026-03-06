package com.example.weatherforecast.presentation.home

import com.example.weatherforecast.presentation.home.model.CurrentWeather
import com.example.weatherforecast.presentation.home.model.DailyForecastItem
import com.example.weatherforecast.presentation.home.model.HourlyItem
import com.example.weatherforecast.presentation.home.model.WeatherStateUIModel


sealed class HomeUiState {
    data class Success(val currentWeather: CurrentWeather, val weather : WeatherStateUIModel, val dailyForecastItems : List<DailyForecastItem>, val hourlyItems : List<HourlyItem>): HomeUiState()
    data class Error(val msg : String) : HomeUiState()
    object Loading : HomeUiState()
}