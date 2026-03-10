package com.example.weatherforecast.presentation.screen.home.model

data class CurrentWeather(
    val iconResId: Int,
    val temperature: String,
    val description: String,
    val maxTemp :Int,
    val minTemp : Int
)