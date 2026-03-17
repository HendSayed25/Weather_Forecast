package com.example.weatherforecast.data.local.dao

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.weatherforecast.data.local.db.AppDatabase
import com.example.weatherforecast.data.local.entity.Alert
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class AlertDaoTest {

    private lateinit var alertDao: AlertDao
    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java).build()

        alertDao = db.alertDao()
    }

    @After
    fun downTear() {
        db.close()
    }

    @Test
    fun addAlert_shouldInsertAlertIntoDatabase() = runTest {
        //Given
        val alert = Alert(
            id = 1,
            title = "Rain Alert",
            time = 1710000000000L,
            date = 1710000000000L,
            type = "Rain",
            condition = "Heavy Rain",
            isEnable = true
        )

        //when
        alertDao.addAlert(alert)
        //then
        val alerts = alertDao.getAllAlerts().first()
        assert(alerts.contains(alert))
    }

    @Test
    fun updateAlert_shouldUpdateAlertInDatabase() = runTest {
        //Given
        val alert = Alert(
            id = 1,
            title = "Rain Alert",
            time = 1710000000000L,
            date = 1710000000000L,
            type = "Rain",
            condition = "Heavy Rain",
            isEnable = true
        )
        alertDao.addAlert(alert)

        val inserted = alertDao.getAllAlerts().first().first()

        val updatedAlert = inserted.copy(title = "Snow Alert")
        alertDao.updateAlert(updatedAlert)

        // Then
        val alerts = alertDao.getAllAlerts().first()
        assert(alerts.first().title == "Snow Alert")
    }

    @Test
    fun deleteAlert_shouldDeleteAlertFromDatabase() = runTest {
        //Given
        val alert = Alert(
            id = 1,
            title = "Rain Alert",
            time = 1710000000000L,
            date = 1710000000000L,
            type = "Rain",
            condition = "Heavy Rain",
            isEnable = true
        )
        alertDao.addAlert(alert)

        //when
        alertDao.deleteAlert(alert)

        //then
        val alerts = alertDao.getAllAlerts().first()
        assert(!alerts.contains(alert))
    }
}