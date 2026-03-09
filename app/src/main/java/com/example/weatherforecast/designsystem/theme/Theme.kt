package com.example.weatherforecast.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.weatherforecast.designsystem.color.LocalWeatherColors
import com.example.weatherforecast.designsystem.color.WeatherColors
import com.example.weatherforecast.designsystem.textstyle.LocalWeatherTextStyle
import com.example.weatherforecast.designsystem.textstyle.WeatherTextStyle

object Theme {
    val color: WeatherColors
        @Composable @ReadOnlyComposable get() = LocalWeatherColors.current

    val textStyle: WeatherTextStyle
        @Composable @ReadOnlyComposable get() = LocalWeatherTextStyle.current

}