package com.example.weatherforecast.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.weatherforecast.designsystem.color.LocalCineVerseColors
import com.example.weatherforecast.designsystem.color.darkThemeColor
import com.example.weatherforecast.designsystem.color.lightThemeColor
import com.example.weatherforecast.designsystem.radius.LocalCineVerseRadius
import com.example.weatherforecast.designsystem.radius.WeatherRadius
import com.example.weatherforecast.designsystem.textstyle.LocalCineVerseTextStyle
import com.example.weatherforecast.designsystem.textstyle.defaultTextStyle

@Composable
fun WeatherForecastTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val theme = if (darkTheme) darkThemeColor else lightThemeColor
    CompositionLocalProvider(
        LocalCineVerseColors provides theme,
        LocalCineVerseTextStyle provides defaultTextStyle,
        LocalCineVerseRadius provides WeatherRadius(),
    ) {
        content()
    }
}