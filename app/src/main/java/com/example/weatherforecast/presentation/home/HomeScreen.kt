package com.example.weatherforecast.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.home.composable.CurrentWeather
import com.example.weatherforecast.presentation.home.composable.LocationDesign
import com.example.weatherforecast.presentation.home.composable.Next5DaysForecastCard
import com.example.weatherforecast.presentation.home.composable.WeatherForecastCard
import com.example.weatherforecast.presentation.home.composable.WeatherStateContent
import com.example.weatherforecast.presentation.home.model.CurrentWeather
import com.example.weatherforecast.presentation.home.model.DailyForecastItem
import com.example.weatherforecast.presentation.home.model.HourlyItem
import com.example.weatherforecast.presentation.home.model.WeatherState
import com.example.weatherforecast.presentation.home.utils.DateUtil
import com.example.weatherforecast.presentation.home.utils.getWeatherStates
import com.example.weatherforecast.presentation.shared.ErrorScreen
import com.example.weatherforecast.presentation.shared.UiState


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val locationState by viewModel.locationUiState.collectAsStateWithLifecycle()
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()

    when (val location = locationState) {
        is UiState.Success -> {
            val weather = weatherState
            if (weather is HomeUiState.Success) {
                val states = getWeatherStates(
                    weatherStates = weather.weather,
                )

                HomeScreenContent(
                    cityName = location.data.cityName,
                    currentWeather = weather.currentWeather,
                    weatherStates = states,
                    dailyForecastItems = weather.dailyForecastItems,
                    hourlyItems = weather.hourlyItems,
                    date = DateUtil.getCurrentFormattedDate(),
                    isDay = 1,
                    modifier = modifier
                )
            }
        }

        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp))
            }
        }

        is UiState.Error -> {
            ErrorScreen(location.msg) {
                viewModel.getCurrentLocation()
            }
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
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 15.dp),
        contentAlignment = Alignment.TopCenter

    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            item { LocationDesign(cityName, isDay, date) }
            item { CurrentWeather(currentWeather, isDay) }
            item { WeatherStateContent(weatherStates) }
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                )
                {
                    items(hourlyItems.size) { item ->
                        WeatherForecastCard(
                            painterResource(hourlyItems[item].imageId),
                            hourlyItems[item].temp.toInt(),
                            hourlyItems[item].time
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
                    textAlign = TextAlign.Start
                )
            }

            item { Next5DaysForecastCard(dailyForecastItems, isDay) }
            item { Spacer(Modifier.height(20.dp)) }
        }
    }
}