package com.example.weatherforecast.presentation.screen.home.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.weatherforecast.R
import com.example.weatherforecast.presentation.screen.home.model.WeatherState
import com.example.weatherforecast.presentation.screen.home.model.WeatherStateUIModel


@Composable
fun getWeatherStates(
    weatherStates: WeatherStateUIModel,
): List<WeatherState> {


    return listOf(
        WeatherState(
            iconId = R.drawable.ic_humidity,
            value = weatherStates.humidity.toString() + " %",
            state = stringResource(R.string.humidity)
        ),
        WeatherState(
            iconId = R.drawable.ic_wind,
            value = weatherStates.windSpeed.toString() + " km/h",
            state = stringResource(R.string.wind)
        ),
        WeatherState(
            iconId = R.drawable.ic_pressure,
            value = weatherStates.pressure.toString() + " hPa",
            state = stringResource(R.string.pressure)
        ),
        WeatherState(
            iconId = R.drawable.ic_couds,
            value = weatherStates.clouds.toString() + " %",
            state = stringResource(R.string.cloud)
        )
    )
}