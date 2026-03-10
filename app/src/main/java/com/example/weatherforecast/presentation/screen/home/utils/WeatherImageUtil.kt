package com.example.weatherforecast.presentation.screen.home.utils

import com.example.weatherforecast.R

object WeatherImageUtil {

    fun getWeatherImage(temperature: Double): Int {
        return when {
            temperature >= 30 -> R.drawable.ic_clear

            temperature in 25.0..29.9 -> R.drawable.ic_clear

            temperature in 20.0..24.9 -> R.drawable.ic_clouds

            temperature in 10.0..19.9 -> R.drawable.ic_clouds

            temperature in 0.0..9.9 -> R.drawable.ic_drizzle

            else -> R.drawable.ic_rain
        }
    }
}