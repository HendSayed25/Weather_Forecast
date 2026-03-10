package com.example.weatherforecast.presentation.screen.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.screen.home.model.CurrentWeather

@Composable
fun CurrentWeather(
    currentWeather: CurrentWeather,
    isDay: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(currentWeather.iconResId),
            contentDescription = "current weather icon",
            modifier = Modifier
                .size(width = 227.dp, height = 200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        WeatherDetails(
            currentWeather = currentWeather,
            isDay = isDay,
        )
    }
}


@Composable
private fun WeatherDetails(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather,
    isDay: Int,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${currentWeather.temperature} °C",
            style = Theme.textStyle.title.xl,
            color = Theme.color.text.primary,
        )

        Text(
            text = currentWeather.description,
            color = Theme.color.text.secondary,
            style = Theme.textStyle.title.md
        )

        Spacer(Modifier.height(8.dp))

        MinMaxDegree(currentWeather.maxTemp, currentWeather.minTemp, isDay)
    }
}

@Preview
@Composable
private fun CurrentWeatherPreview() {
    fun getFakeCurrentWeather(): CurrentWeather {
        return CurrentWeather(
            iconResId = R.drawable.ic_clouds,
            temperature = "26",
            description = "Clear Sky",
            maxTemp = 30,
            minTemp = 22
        )
    }
    CurrentWeather(
        currentWeather = getFakeCurrentWeather(),
        isDay = 1,
    )
}