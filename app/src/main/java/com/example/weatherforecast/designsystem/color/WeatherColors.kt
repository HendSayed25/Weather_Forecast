package com.example.weatherforecast.designsystem.color

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush

data class WeatherColors(
    val background: Background,
    val text: TextColor,
)

data class Background(
    val screen: Color,
    val card: Color
)

data class TextColor(
    val primary: Color,
    val secondary: Color,
    val tertiary: Brush,
)

internal val LocalWeatherColors = staticCompositionLocalOf { lightThemeColor }