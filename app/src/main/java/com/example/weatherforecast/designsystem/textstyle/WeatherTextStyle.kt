package com.example.weatherforecast.designsystem.textstyle


import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import retrofit2.http.Body

data class WeatherTextStyle(
    val display: Display,
    val title: Title,
)

data class Display(
    val xl: TextStyle
)

data class Title(
    val xl: TextStyle,
    val lg: TextStyle,
    val md: TextStyle,
    val sm: TextStyle
)

internal val LocalWeatherTextStyle = staticCompositionLocalOf { defaultTextStyle }