package com.example.weatherforecast.data.remote.datasource

import android.content.Context
import android.location.Geocoder
import com.example.weatherforecast.data.remote.model.Address
import com.example.weatherforecast.data.remote.model.Coordinate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressRemoteDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getLocationAddress(coordinate: Coordinate): Address {
        return withContext(Dispatchers.IO) {
            val geocoder = Geocoder(context)
            var cityName = ""
            var countryName = ""

            try {
                val addresses = geocoder.getFromLocation(
                    coordinate.lat,
                    coordinate.long,
                    1
                )

                if (!addresses.isNullOrEmpty()) {
                    cityName = addresses[0].subAdminArea
                    countryName = addresses[0].countryName
                } else {
                    cityName = ""
                    countryName = ""
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            Address(cityName, countryName)
        }
    }
}