package com.example.weatherforecast.data.remote.datasource

import android.content.Context
import android.location.Geocoder
import com.example.weatherforecast.data.remote.model.Coordinate

class AddressRemoteDataSource {

    fun getCityName(coordinate : Coordinate, context: Context): String {
        val geocoder = Geocoder(context)
        var cityName = ""

        try {
            val addresses = geocoder.getFromLocation(
                coordinate.lat,
                coordinate.long,
                1
            )

            if (!addresses.isNullOrEmpty()) {
                cityName = addresses[0].subAdminArea
            } else {
                cityName = ""
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return cityName
    }
}