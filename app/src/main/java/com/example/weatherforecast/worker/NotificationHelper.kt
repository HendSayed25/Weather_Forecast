package com.example.weatherforecast.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weatherforecast.MainActivity
import com.example.weatherforecast.R
import com.example.weatherforecast.worker.Constants.ALARM_CHANNEL_ID
import com.example.weatherforecast.worker.Constants.ALARM_CHANNEL_NAME
import com.example.weatherforecast.worker.Constants.ALERT_ID_KEY
import com.example.weatherforecast.worker.Constants.ALERT_MESSAGE_KEY
import com.example.weatherforecast.worker.Constants.ALERT_NAVIGATE_KEY
import com.example.weatherforecast.worker.Constants.ALERT_NAVIGATE_VALUE
import com.example.weatherforecast.worker.Constants.ALERT_TITLE_KEY
import com.example.weatherforecast.worker.Constants.NOTIFICATION_CHANNEL_ID
import com.example.weatherforecast.worker.Constants.NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@RequiresApi(Build.VERSION_CODES.O)
@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val manager = context.getSystemService(NotificationManager::class.java)
    private var mediaPlayer: MediaPlayer? = null

    fun sendNotification(alertId: Int, title: String, message: String) {
        createChannelIfNeeded(
            channelId = NOTIFICATION_CHANNEL_ID,
            channelName = NOTIFICATION_CHANNEL_NAME,
            importance = NotificationManager.IMPORTANCE_HIGH
        )

        val pendingIntent = createMainActivityPendingIntent(alertId)

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.alert)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(alertId, notification)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun sendAlarm(alertId: Int, title: String, message: String) {
        playSound()

        val dismissIntent = Intent(context, AlarmActionReceiver::class.java).apply {
            action = context.getString(R.string.dismiss_action)
            putExtra(ALERT_ID_KEY, alertId)
        }

        val dismissPending = PendingIntent.getBroadcast(
            context, alertId, dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozeIntent = Intent(context, AlarmActionReceiver::class.java).apply {
            action = context.getString(R.string.snooze_action)
            putExtra(ALERT_ID_KEY, alertId)
            putExtra(ALERT_TITLE_KEY, title)
            putExtra(ALERT_MESSAGE_KEY,message)
        }

        val snoozePending = PendingIntent.getBroadcast(
            context, alertId + 1, snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        createChannelIfNeeded(
            channelId = ALARM_CHANNEL_ID,
            channelName = ALARM_CHANNEL_NAME,
            importance = NotificationManager.IMPORTANCE_HIGH,
            isAlarm = true
        )

        val pendingIntent = createMainActivityPendingIntent(alertId)

        val notification = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(R.drawable.alert)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(pendingIntent)
            .addAction(0,context.getString(R.string.dismiss_title) , dismissPending)
            .addAction(0, context.getString(R.string.snooze_title,2), snoozePending)
            .setOngoing(true)
            .build()

        manager.notify(alertId, notification)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun playSound() {
        if (mediaPlayer?.isPlaying == true) return
        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            setDataSource(context, alarmUri)
            isLooping = true
            prepare()
            start()
        }
    }

    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private fun createChannelIfNeeded(
        channelId: String,
        channelName: String,
        importance: Int,
        isAlarm: Boolean = false
    ) {
        if (manager.getNotificationChannel(channelId) != null) return

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            if (isAlarm) {
                enableVibration(true)
            }
        }
        manager.createNotificationChannel(channel)
    }

    private fun createMainActivityPendingIntent(alertId: Int): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(ALERT_NAVIGATE_KEY, ALERT_NAVIGATE_VALUE)
        }
        return PendingIntent.getActivity(
            context, alertId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}