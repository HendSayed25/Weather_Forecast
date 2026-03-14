package com.example.weatherforecast.worker

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherforecast.presentation.utils.AlertType
import com.example.weatherforecast.worker.Constants.ALERT_ID_KEY
import com.example.weatherforecast.worker.Constants.ALERT_MESSAGE_KEY
import com.example.weatherforecast.worker.Constants.ALERT_MESSAGE_KEY_DEFAULT_VALUE
import com.example.weatherforecast.worker.Constants.ALERT_TITLE_KEY
import com.example.weatherforecast.worker.Constants.ALERT_TITLE_KEY_DEFAULT_VALUE
import com.example.weatherforecast.worker.Constants.ALERT_TYPE_KEY
import com.example.weatherforecast.worker.Constants.ALERT_TYPE_KEY_DEFAULT_VALUE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmActionReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationHelper: NotificationHelper
    @Inject
    lateinit var scheduler: AlertScheduler

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onReceive(context: Context, intent: Intent) {
        val alertId = intent.getIntExtra(ALERT_ID_KEY, -1)
        val title = intent.getStringExtra(ALERT_TITLE_KEY) ?: ALERT_TITLE_KEY_DEFAULT_VALUE
        val message = intent.getStringExtra(ALERT_MESSAGE_KEY) ?: ALERT_MESSAGE_KEY_DEFAULT_VALUE
        val type = intent.getStringExtra(ALERT_TYPE_KEY) ?: ALERT_TYPE_KEY_DEFAULT_VALUE

        val manager = context.getSystemService(NotificationManager::class.java)

        when (intent.action) {
            "DISMISS" -> {
                notificationHelper.stopSound()
                manager.cancel(alertId)
            }

            "SNOOZE" -> {
                notificationHelper.stopSound()
                manager.cancel(alertId)
                scheduler.snoozeAlert(
                    alertId = alertId,
                    message = message,
                    title = title,
                    minutes = 2
                )
            }

            else -> { //when scheduleDailyWork function called without action will send null action
                when (type) {
                    AlertType.NOTIFICATION.name.lowercase() -> notificationHelper.sendNotification(
                        alertId,
                        title,
                        message
                    )

                    AlertType.ALARM.name.lowercase() -> notificationHelper.sendAlarm(
                        alertId,
                        title,
                        message
                    )
                }
            }
        }
    }
}