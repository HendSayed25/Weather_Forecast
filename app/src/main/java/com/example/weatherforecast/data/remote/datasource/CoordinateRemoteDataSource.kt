package com.example.weatherforecast.data.remote.datasource

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.example.weatherforecast.data.remote.model.Coordinate
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.coroutines.resume

class CoordinateRemoteDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Coordinate {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val lastKnown = getLastKnownLocation(fusedLocationClient)
        if (lastKnown != null) return lastKnown

        return withTimeoutOrNull(10_000L) {
            requestFreshLocation(fusedLocationClient)
        } ?: Coordinate(lat = 0.0, long = 0.0)
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastKnownLocation(
        fusedLocationClient: FusedLocationProviderClient
    ): Coordinate? {
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        continuation.resume(
                            Coordinate(lat = location.latitude, long = location.longitude)
                        )
                    } else {
                        continuation.resume(null)
                    }
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun requestFreshLocation(
        fusedLocationClient: FusedLocationProviderClient
    ): Coordinate {
        return suspendCancellableCoroutine { continuation ->
            val request = LocationRequest.Builder(3000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMaxUpdates(1)
                .build()

            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val lastLocation = result.lastLocation ?: return
                    continuation.resume(
                        Coordinate(lat = lastLocation.latitude, long = lastLocation.longitude)
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