package com.example.weatherforecast.designsystem.color

import android.R.id.primary
import androidx.compose.ui.graphics.BlendMode.Companion.Overlay
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val lightThemeColor = WeatherColors(
    text = TextColor(
        primary = Color(0xFF292929),
        secondary = Color(0xFF111111),
        tertiary = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF292929),
                Color(0xFFFFFFFF)
            )
        )
    ),
    background = Background(
        screen = Color(0xFFD9D9D9),
        morningCard = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFF88508),
                Color(0xFFF6FAD9)
            )
        ),
        nightCard = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF443D64),
                Color(0xFF6582C6)
            )
        ),
    )
)