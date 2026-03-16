package com.example.weatherforecast.presentation.screen.home.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.WindSpeedUnit
import com.example.weatherforecast.presentation.screen.home.model.WeatherState
import com.example.weatherforecast.presentation.screen.home.model.WeatherStateUIModel
import com.example.weatherforecast.presentation.utils.LanguageUtilUtils.getWindUnitLabel


@Composable
fun getWeatherStates(
    weatherStates: WeatherStateUIModel, windSpeedUnit: WindSpeedUnit, language: Language
): List<WeatherState> {

    return listOf(
        WeatherState(
            iconId = R.drawable.ic_humidity,
            value = weatherStates.humidity,
            state = stringResource(R.string.humidity),
            unit = "%"
        ), WeatherState(
            iconId = R.drawable.ic_wind,
            value = weatherStates.windSpeed.toInt(),
            state = stringResource(R.string.wind),
            unit = getWindUnitLabel(language, windSpeedUnit)
        ), WeatherState(
            iconId = R.drawable.ic_pressure,
            value = weatherStates.pressure.toInt(),
            state = stringResource(R.string.pressure),
            unit = if (language.code == "ar") "هكتوباسكال" else "hPa"
        ), WeatherState(
            iconId = R.drawable.ic_couds,
            value = weatherStates.clouds.toInt(),
            state = stringResource(R.string.cloud),
            unit = "%"
        )
    )
}