package com.example.weatherforecast.presentation.home.mapper


import com.example.weatherforecast.data.remote.response.ForecastResponse
import com.example.weatherforecast.data.remote.response.WeatherResponse
import com.example.weatherforecast.presentation.home.model.CurrentWeather
import com.example.weatherforecast.presentation.home.model.DailyForecastItem
import com.example.weatherforecast.presentation.home.model.HourlyItem
import com.example.weatherforecast.presentation.home.model.WeatherStateUIModel
import com.example.weatherforecast.presentation.home.utils.DateUtil
import com.example.weatherforecast.presentation.home.utils.WeatherImageUtil

fun WeatherResponse.toCurrentWeatherUi(): CurrentWeather {
    return CurrentWeather(
        iconResId = WeatherImageUtil.getWeatherImage(main.temp ?: 0.0),
        temperature = (main.temp ?: 0.0).toInt().toString(),
        description = weather.firstOrNull()?.description ?: "",
        maxTemp = (main.tempMax ?: 0.0).toInt(),
        minTemp = (main.tempMin ?: 0.0).toInt()
    )
}

fun WeatherResponse.toWeatherUiModel(): WeatherStateUIModel {
    return WeatherStateUIModel(
        windSpeed = wind.speed,
        humidity = main.humidity?.toInt() ?: 0,
        pressure = main.pressure?.toDouble() ?: 0.0,
        visibility = visibility.toDouble(),
        sunrise = sys.sunrise.toString(),
        sunset = sys.sunset.toString(),
        clouds = clouds.all?.toLong() ?: 0
    )
}

fun ForecastResponse.toHourly(): List<HourlyItem> {
    return list?.take(8)?.map {
        HourlyItem(
            time = DateUtil.getTime(it.dtTxt),
            temp = (it.main?.temp ?: 0.0),
            imageId = WeatherImageUtil.getWeatherImage(it.main?.temp ?: 0.0)
        )
    } ?: emptyList()
}

fun ForecastResponse.toDaily(): List<DailyForecastItem> {
    return list
        ?.filter { it.dtTxt?.contains("12:00:00") == true }
        ?.map { item ->

            DailyForecastItem(
                day = DateUtil.getDay(item.dtTxt),
                minTemp = (item.main?.tempMin ?: 0.0),
                maxTemp = (item.main?.tempMax ?: 0.0),
                imageId = WeatherImageUtil.getWeatherImage(item.main?.temp ?: 0.0)
            )
        }
        ?: emptyList()
}