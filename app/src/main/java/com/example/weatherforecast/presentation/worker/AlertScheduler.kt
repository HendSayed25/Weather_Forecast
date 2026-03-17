package com.example.weatherforecast.presentation.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.entity.Alert
import com.example.weatherforecast.presentation.utils.AlertType
import com.example.weatherforecast.presentation.worker.Constants.ALERT_CONDITION_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_ID_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_MESSAGE_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_TITLE_KEY
import com.example.weatherforecast.presentation.worker.Constants.ALERT_TYPE_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlertScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun scheduleAlert(alert: Alert) {
        when {
            alert.condition.isNotEmpty() -> scheduleConditionWork(alert)
            else -> scheduleDailyWork(alert)
        }
    }

    private fun scheduleDailyWork(alert: Alert) {
        val now = System.currentTimeMillis()
        val message = if (alert.condition.isNotEmpty()) context.getString(
            R.string.weather_condition_message,
            alert.condition
        ) else context.getString(R.string.message_alarm_without_condition)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = alert.time
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= now) add(Calendar.DAY_OF_YEAR, 1)
        }

        val intent = Intent(context, AlarmActionReceiver::class.java).apply {
            putExtra(ALERT_ID_KEY, alert.id)
            putExtra(ALERT_TITLE_KEY, alert.title)
            putExtra(ALERT_TYPE_KEY, alert.type.lowercase())
            putExtra(ALERT_MESSAGE_KEY, message)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, alert.id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun scheduleConditionWork(alert: Alert) {//every 15 min check the condition
        val request = PeriodicWorkRequestBuilder<ConditionWorker>(15, TimeUnit.MINUTES)
            .setInputData(buildData(alert))
            .build()

        workManager.enqueueUniquePeriodicWork(
            "condition_alert_${alert.id}",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancelAlert(alertId: Int?) {
        val intent = Intent(context, AlarmActionReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, alertId ?: 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)

        workManager.cancelUniqueWork("alert_$alertId")
        workManager.cancelUniqueWork("condition_alert_$alertId")
    }

    fun snoozeAlert(alertId: Int, message: String, title: String, minutes: Int = 2) {
        val delay = TimeUnit.MINUTES.toMillis(minutes.toLong())

        val intent = Intent(context, AlarmActionReceiver::class.java).apply {
            putExtra(ALERT_ID_KEY, alertId)
            putExtra(ALERT_TITLE_KEY, title)
            putExtra(ALERT_TYPE_KEY, AlertType.ALARM.name.lowercase())
            putExtra(ALERT_MESSAGE_KEY, message)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, alertId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + delay,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + delay,
                pendingIntent
            )
        }
    }

    private fun buildData(alert: Alert) = Data.Builder()
        .putInt(ALERT_ID_KEY, alert.id)
        .putString(ALERT_TITLE_KEY, alert.title)
        .putString(ALERT_TYPE_KEY, alert.type.lowercase())
        .putString(ALERT_CONDITION_KEY, alert.condition)
        .putString(
            ALERT_MESSAGE_KEY,
            if (alert.condition.isNotEmpty()) context.getString(
                R.string.weather_condition_message,
                alert.condition
            ) else context.getString(R.string.message_alarm_without_condition)
        )
        .build()
}