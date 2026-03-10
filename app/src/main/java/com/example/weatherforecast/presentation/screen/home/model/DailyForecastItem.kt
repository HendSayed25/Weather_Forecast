package com.example.weatherforecast.presentation.screen.home.model

data class DailyForecastItem(
    val day: String,
    val minTemp: Double,
    val maxTemp: Double,
    val imageId: Int,
)