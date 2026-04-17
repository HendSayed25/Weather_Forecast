package com.example.weatherforecast.presentation.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val pendingResult = goAsync() // to tell android that I need extra time to finish my work

            val entryPoint = EntryPointAccessors.fromApplication(
                context.applicationContext,
                BootEntryPoint::class.java
            )

            val repository = entryPoint.alertRepository()
            val scheduler = entryPoint.alertScheduler()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val alerts = repository.getAllAlerts().first()

                    alerts.forEach { alert ->
                        if (alert.isEnable) {
                            scheduler.scheduleAlert(alert)
                        }
                    }
                } finally {
                    pendingResult.finish() //to safely close the receiver
                }
            }
        }
    }
}