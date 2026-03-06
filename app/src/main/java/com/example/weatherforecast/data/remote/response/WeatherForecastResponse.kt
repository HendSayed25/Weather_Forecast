package com.example.weatherforecast.data.remote.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(

    @SerializedName("cod")
    val cod: String? = null,

    @SerializedName("message")
    val message: Int? = null,

    @SerializedName("cnt")
    val cnt: Int? = null,

    @SerializedName("list")
    val list: List<ForecastItem>? = null,

    @SerializedName("city")
    val city: City? = null
)

data class ForecastItem(

    @SerializedName("dt")
    val dt: Long? = null,

    @SerializedName("main")
    val main: MainWeather? = null,

    @SerializedName("weather")
    val weather: List<Weather>? = null,

    @SerializedName("clouds")
    val clouds: Clouds? = null,

    @SerializedName("wind")
    val wind: WindForecast? = null,

    @SerializedName("visibility")
    val visibility: Int? = null,

    @SerializedName("pop")
    val pop: Double? = null,

    @SerializedName("sys")
    val sys: SysForecast? = null,

    @SerializedName("dt_txt")
    val dtTxt: String? = null
)

data class MainWeather(

    @SerializedName("temp")
    val temp: Double? = null,

    @SerializedName("feels_like")
    val feelsLike: Double? = null,

    @SerializedName("temp_min")
    val tempMin: Double? = null,

    @SerializedName("temp_max")
    val tempMax: Double? = null,

    @SerializedName("pressure")
    val pressure: Int? = null,

    @SerializedName("sea_level")
    val seaLevel: Int? = null,

    @SerializedName("grnd_level")
    val grndLevel: Int? = null,

    @SerializedName("humidity")
    val humidity: Int? = null,

    @SerializedName("temp_kf")
    val tempKf: Double? = null
)

data class WindForecast(

    @SerializedName("speed")
    val speed: Double? = null,

    @SerializedName("deg")
    val deg: Int? = null,

    @SerializedName("gust")
    val gust: Double? = null
)

data class SysForecast(

    @SerializedName("pod")
    val pod: String? = null
)

data class City(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("coord")
    val coord: Coord? = null,

    @SerializedName("country")
    val country: String? = null,

    @SerializedName("population")
    val population: Int? = null,

    @SerializedName("timezone")
    val timezone: Int? = null,

    @SerializedName("sunrise")
    val sunrise: Long? = null,

    @SerializedName("sunset")
    val sunset: Long? = null
)