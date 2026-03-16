package com.example.weatherforecast.data.remote.datasource

import com.example.weatherforecast.data.remote.network.WeatherApiService
import com.example.weatherforecast.data.remote.response.GeoResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class GeoRemoteDataSource @Inject constructor(
    private val apiService: WeatherApiService
) {


    suspend fun getCityNameByCoordinates(
        lat: Double,
        lon: Double,
        apiKey: String,
        lang: String = "en",
        limit: Int = 1
    ): String? = withContext(Dispatchers.IO) {
        try {
            val response: Response<List<GeoResponse>> =
                apiService.getReverseGeo(lat, lon, limit, apiKey)
            if (response.isSuccessful) {
                val body = response.body()
                if (!body.isNullOrEmpty()) {
                    val first: GeoResponse = body.first()
                    return@withContext first.localNames?.get(lang) ?: first.name
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}