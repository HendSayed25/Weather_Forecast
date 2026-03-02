package com.example.weatherforecast.designsystem.color

import androidx.compose.ui.graphics.BlendMode.Companion.Overlay
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val darkThemeColor = WeatherColors(
    text = TextColor(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFF111111),
        tertiary = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF292929),
                Color(0xFFFFFFFF)
            )
        )
    ),
    background = Background(
        screen = Color(0xFF444444),
        morningCard = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF373636),
                Color(0xFF373636)
            )
        ),
        nightCard = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF373636),
                Color(0xFF373636)
            )
        ),
    )
)