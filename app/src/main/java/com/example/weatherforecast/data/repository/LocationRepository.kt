package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.remote.datasource.AddressRemoteDataSource
import com.example.weatherforecast.data.remote.datasource.CoordinateRemoteDataSource
import com.example.weatherforecast.data.remote.model.Address
import com.example.weatherforecast.data.remote.model.Coordinate
import com.example.weatherforecast.data.remote.model.Location
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class LocationRepository @Inject constructor(
    private val coordinateDataSource: CoordinateRemoteDataSource,
    private val addressRemoteDataSource: AddressRemoteDataSource,
    private val appDataStore: AppDataStore
) {
    suspend fun getCurrentLocation(isUpdate: Boolean = false): Result<Location> {

        return try {
            var coordinate = appDataStore.location.first()
            var address: Address

            if ((!isUpdate) && (coordinate.lat != 0.0 && coordinate.long != 0.0)) {
                address = addressRemoteDataSource.getLocationAddress(coordinate)
            } else {
                coordinate = coordinateDataSource.getCurrentLocation()
                address = addressRemoteDataSource.getLocationAddress(coordinate)
            }

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

    suspend fun getLocationDetails(lat: Double, long: Double): Result<Address> {
        return try {
            val address = addressRemoteDataSource.getLocationAddress(Coordinate(lat, long))
            Result.success(address)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}