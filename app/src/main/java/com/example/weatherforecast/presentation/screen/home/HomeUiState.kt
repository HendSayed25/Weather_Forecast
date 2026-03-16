package com.example.weatherforecast.presentation.screen.home

import com.example.weatherforecast.presentation.screen.home.model.CurrentWeather
import com.example.weatherforecast.presentation.screen.home.model.DailyForecastItem
import com.example.weatherforecast.presentation.screen.home.model.HourlyItem
import com.example.weatherforecast.presentation.screen.home.model.WeatherStateUIModel


sealed class HomeUiState {
    data class Success(val currentWeather: CurrentWeather, val weather : WeatherStateUIModel, val dailyForecastItems : List<DailyForecastItem>, val hourlyItems : List<HourlyItem>): HomeUiState()
    data class Error(val msgId : Int) : HomeUiState()
    object Loading : HomeUiState()
}