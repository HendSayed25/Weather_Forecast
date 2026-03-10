package com.example.weatherforecast.presentation.screen.home.model

data class WeatherStateUIModel(
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Double,
    val visibility: Double,
    val sunrise: String,
    val sunset: String,
    val clouds : Long,
)