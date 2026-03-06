package com.example.weatherforecast.data.remote.datasource

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.example.weatherforecast.data.remote.model.Coordinate
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class CoordinateRemoteDataSource {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): Coordinate {

        return suspendCancellableCoroutine { continuation ->

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            val request = LocationRequest.Builder(3000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            val callback = object : LocationCallback() {

                override fun onLocationResult(result: LocationResult) {
                    val lastLocation = result.lastLocation ?: return
                    continuation.resume(
                        Coordinate(
                            lat = lastLocation.latitude,
                            long = lastLocation.longitude
                        )
                    )

                    fusedLocationClient.removeLocationUpdates(this)
                }
            }

            fusedLocationClient.requestLocationUpdates(
                request,
                callback,
                Looper.getMainLooper()
            )

            continuation.invokeOnCancellation {
                fusedLocationClient.removeLocationUpdates(callback)
            }
        }
    }
}