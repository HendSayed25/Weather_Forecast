package com.example.weatherforecast.data.repository

import android.content.Context
import com.example.weatherforecast.data.remote.datasource.AddressRemoteDataSource
import com.example.weatherforecast.data.remote.datasource.CoordinateRemoteDataSource
import com.example.weatherforecast.data.remote.model.Location


class LocationRepository(
    private val coordinateDataSource: CoordinateRemoteDataSource,
    private val addressRemoteDataSource: AddressRemoteDataSource
) {
    suspend fun getCurrentLocation(context: Context): Result<Location> {

        return try {
            val coordinate = coordinateDataSource.getCurrentLocation(context)
            val cityName = addressRemoteDataSource.getCityName(coordinate, context)

            Result.success(
                Location(
                    lat = coordinate.lat,
                    long = coordinate.long,
                    cityName = cityName
                )
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}