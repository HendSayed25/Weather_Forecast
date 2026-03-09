package com.example.weatherforecast.designsystem.color

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val lightThemeColor = WeatherColors(
    text = TextColor(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xB3FFFFFF),
        tertiary = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFFFFF),
                Color(0x80FFFFFF)
            )
        )
    ),
    background = Background(
        screen = Color(0xFF2E6DB4),
        card = Color(0xFF4A90D9)
    )
)