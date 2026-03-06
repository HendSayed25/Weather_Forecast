package com.example.weatherforecast.data.remote.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.*

@Serializable
data class WeatherResponse (
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)

data class Clouds(

    @SerializedName("all")
    val all: Int? = null
)

data class Coord(

    @SerializedName("lat")
    val lat: Double? = null,

    @SerializedName("lon")
    val lon: Double? = null
)

data class Main(
    @SerializedName("temp")
    val temp: Double? = null,

    @SerializedName("feels_like")
    val feelsLike: Double? = null,

    @SerializedName("temp_min")
    val tempMin: Double? = null,

    @SerializedName("temp_max")
    val tempMax: Double? = null,

    @SerializedName("pressure")
    val pressure: Long? = null,

    @SerializedName("humidity")
    val humidity: Long? = null,

    @SerializedName("sea_level")
    val seaLevel: Long? = null,

    @SerializedName("grnd_level")
    val grndLevel: Long? = null
)

@Serializable
data class Sys (
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Weather(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("main")
    val main: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("icon")
    val icon: String? = null
)

@Serializable
data class Wind (
    val speed: Double,
    val deg: Long
)
