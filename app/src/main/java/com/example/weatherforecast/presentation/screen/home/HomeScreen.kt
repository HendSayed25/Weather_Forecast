package com.example.weatherforecast.presentation.screen.home

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.screen.home.composable.CurrentWeather
import com.example.weatherforecast.presentation.screen.home.composable.LocationDesign
import com.example.weatherforecast.presentation.screen.home.composable.Next5DaysForecastCard
import com.example.weatherforecast.presentation.screen.home.composable.WeatherForecastCard
import com.example.weatherforecast.presentation.screen.home.composable.WeatherStateContent
import com.example.weatherforecast.presentation.screen.home.model.CurrentWeather
import com.example.weatherforecast.presentation.screen.home.model.DailyForecastItem
import com.example.weatherforecast.presentation.screen.home.model.HourlyItem
import com.example.weatherforecast.presentation.screen.home.model.WeatherState
import com.example.weatherforecast.presentation.screen.home.utils.DateUtil
import com.example.weatherforecast.presentation.screen.home.utils.getWeatherStates
import com.example.weatherforecast.presentation.screen.shared.UiState
import com.example.weatherforecast.presentation.screen.shared.composable.ErrorScreen
import com.example.weatherforecast.presentation.screen.shared.composable.Loading
import requestLocationPermissionWithSettingsRedirect


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()
) {
    val locationState by viewModel.locationUiState.collectAsStateWithLifecycle()
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    val tempUnit by viewModel.tempUnit.collectAsStateWithLifecycle()
    val windSpeedUnit by viewModel.windSpeedUnit.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val activity = context as? Activity ?: return

    val requestLocationPermission = requestLocationPermissionWithSettingsRedirect(
        onGranted = viewModel::getLocation, activity = activity
    )

    LaunchedEffect(Unit) {
        requestLocationPermission()
    }

    when (val weather = weatherState) {
        is HomeUiState.Loading -> {
            Loading()
        }

        is HomeUiState.Error -> {
            ErrorScreen(stringResource(weather.msgId)) {
                viewModel.getLocation()
            }
        }

        is HomeUiState.Success -> {
            val location = locationState
            val cityName = if (location is UiState.Success) location.data.cityName else ""

            val states = getWeatherStates(weatherStates = weather.weather, windSpeedUnit, language)

            HomeScreenContent(
                cityName = cityName,
                currentWeather = weather.currentWeather,
                weatherStates = states,
                dailyForecastItems = weather.dailyForecastItems,
                hourlyItems = weather.hourlyItems,
                date = DateUtil.getCurrentFormattedDate(),
                isDay = 1,
                modifier = modifier,
                language = language,
                tempUnit = tempUnit
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    cityName: String,
    currentWeather: CurrentWeather,
    weatherStates: List<WeatherState>,
    dailyForecastItems: List<DailyForecastItem>,
    hourlyItems: List<HourlyItem>,
    date: String,
    isDay: Int,
    tempUnit: TempUnit,
    language: Language,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val isRtl = language.code == "ar"

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen),

        ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally, state = listState
        ) {
            item { LocationDesign(cityName, isDay, date, language) }
            item { CurrentWeather(currentWeather, language, tempUnit, isDay) }
            item { WeatherStateContent(weatherStates, languageCode = language.code) }
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    reverseLayout = isRtl
                ) {
                    items(hourlyItems.size) { item ->
                        WeatherForecastCard(
                            painterResource(hourlyItems[item].imageId),
                            hourlyItems[item].temp.toInt(),
                            hourlyItems[item].time,
                            language
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(60.dp)) }

            stickyHeader {
                Text(
                    text = stringResource(R.string.next5Days),
                    style = Theme.textStyle.title.lg,
                    color = Theme.color.text.primary,
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    textAlign = if (isRtl) TextAlign.Start else TextAlign.End,
                )
            }

            item { Next5DaysForecastCard(dailyForecastItems, isDay, language, tempUnit) }
            item {
                Spacer(
                    Modifier
                        .height(20.dp)
                        .background(Theme.color.background.screen)
                )
            }
        }
    }
}