package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.remote.datasource.AddressRemoteDataSource
import com.example.weatherforecast.data.remote.datasource.CoordinateRemoteDataSource
import com.example.weatherforecast.data.remote.model.Location
import javax.inject.Inject


class LocationRepository @Inject constructor(
    private val coordinateDataSource: CoordinateRemoteDataSource,
    private val addressRemoteDataSource: AddressRemoteDataSource
) {
    suspend fun getCurrentLocation(): Result<Location> {

        return try {
            val coordinate = coordinateDataSource.getCurrentLocation()
            val address = addressRemoteDataSource.getLocationAddress(coordinate)

            Result.success(
                Location(
                    lat = coordinate.lat,
                    long = coordinate.long,
                    cityName = address.cityName,
                    countryName = address.countryName
                )
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}