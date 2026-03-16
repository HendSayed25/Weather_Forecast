package com.example.weatherforecast.data.repository

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.local.datastore.toApiLang
import com.example.weatherforecast.data.remote.datasource.AddressRemoteDataSource
import com.example.weatherforecast.data.remote.datasource.CoordinateRemoteDataSource
import com.example.weatherforecast.data.remote.datasource.GeoRemoteDataSource
import com.example.weatherforecast.data.remote.model.Address
import com.example.weatherforecast.data.remote.model.Coordinate
import com.example.weatherforecast.data.remote.model.Location
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class LocationRepository @Inject constructor(
    private val coordinateDataSource: CoordinateRemoteDataSource,
    private val addressRemoteDataSource: AddressRemoteDataSource,
    private val geoRemoteDataSource: GeoRemoteDataSource,
    private val appDataStore: AppDataStore
) {
    suspend fun getCurrentLocation(isUpdate: Boolean): Result<Location> {

        return try {
            var coordinate = appDataStore.location.first()
            val language = appDataStore.language.first()
            var address: Address

            if ((!isUpdate) && (coordinate.lat != 0.0 && coordinate.long != 0.0)) {
                address = addressRemoteDataSource.getLocationAddress(coordinate)
            } else {
                coordinate = coordinateDataSource.getCurrentLocation()
                address = addressRemoteDataSource.getLocationAddress(coordinate)
            }

            val cityName = geoRemoteDataSource.getCityNameByCoordinates(
                lat = coordinate.lat,
                lon = coordinate.long,
                apiKey = BuildConfig.WEATHER_API_KEY,
                lang = language.toApiLang()
            )

            Result.success(
                Location(
                    lat = coordinate.lat,
                    long = coordinate.long,
                    cityName = cityName ?: address.cityName,
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