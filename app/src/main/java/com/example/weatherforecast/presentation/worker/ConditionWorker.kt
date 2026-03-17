package com.example.weatherforecast.presentation.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.remote.response.WeatherResponse
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.presentation.utils.AlertType
import com.example.weatherforecast.presentation.worker.Constants.ALERT_CONDITION_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_ID_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_MESSAGE_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_MESSAGE_KEY_DEFAULT_VALUE
import com.example.weatherforecast.presentation.worker.Constants.ALERT_TITLE_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_TITLE_KEY_DEFAULT_VALUE
import com.example.weatherforecast.presentation.worker.Constants.ALERT_TYPE_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_TYPE_KEY_DEFAULT_VALUE
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@RequiresApi(Build.VERSION_CODES.O)
@HiltWorker
class ConditionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val notificationHelper: NotificationHelper,
    private val appDataStore: AppDataStore
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val alertId   = inputData.getInt(ALERT_ID_KEY, -1)
        val condition = inputData.getString( ALERT_CONDITION_KEY) ?: return Result.failure()
        val type      = inputData.getString(ALERT_TYPE_KEY) ?: ALERT_TYPE_KEY_DEFAULT_VALUE
        val title     = inputData.getString(ALERT_TITLE_KEY) ?:  ALERT_TITLE_KEY_DEFAULT_VALUE
        val message   = inputData.getString(ALERT_MESSAGE_KEY) ?: ALERT_MESSAGE_KEY_DEFAULT_VALUE

        val location = appDataStore.location.first()
        val currentWeather = weatherRepository.getCurrentWeather(location.lat,location.long)

        return if (currentWeather.isSuccess) {
            val weather = currentWeather.getOrNull() ?: return Result.failure()

            if (isConditionMet(condition, weather)) {
                when (type) {
                    AlertType.NOTIFICATION.name.lowercase() -> notificationHelper.sendNotification(alertId, title, message)
                    AlertType.ALARM.name.lowercase() -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        notificationHelper.sendAlarm(alertId, title, message)
                    }
                }
            }
            Result.success()
        } else {
            Result.retry()
        }
    }

    private fun isConditionMet(condition: String, weather: WeatherResponse): Boolean {
        val currentCondition = weather.weather[0].description.lowercase()
        return condition.lowercase() == currentCondition
    }
}