package com.example.weatherforecast.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.weatherforecast.designsystem.color.LocalCineVerseColors
import com.example.weatherforecast.designsystem.color.WeatherColors
import com.example.weatherforecast.designsystem.radius.LocalCineVerseRadius
import com.example.weatherforecast.designsystem.radius.WeatherRadius
import com.example.weatherforecast.designsystem.textstyle.LocalCineVerseTextStyle
import com.example.weatherforecast.designsystem.textstyle.WeatherTextStyle

object Theme {
    val color: WeatherColors
        @Composable @ReadOnlyComposable get() = LocalCineVerseColors.current

    val textStyle: WeatherTextStyle
        @Composable @ReadOnlyComposable get() = LocalCineVerseTextStyle.current

    val radius: WeatherRadius
        @Composable @ReadOnlyComposable get() = LocalCineVerseRadius.current

}