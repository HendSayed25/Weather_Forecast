package com.example.weatherforecast.designsystem.color

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val darkThemeColor = WeatherColors(
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
        screen = Color(0xFF0F2D52),
        card = Color(0xFF1B4F8A),
    )
)