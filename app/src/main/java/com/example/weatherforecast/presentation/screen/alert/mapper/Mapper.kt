package com.example.weatherforecast.presentation.screen.alert.mapper

import com.example.weatherforecast.data.local.entity.Alert
import com.example.weatherforecast.presentation.screen.alert.model.AlertModel
import com.example.weatherforecast.presentation.utils.TimeUtils

fun Alert.toUiModel(languageCode : String) : AlertModel {
    return AlertModel(
        id = id,
        title = title,
        formattedTime = TimeUtils.formatTime(time,languageCode),
        type = type,
        condition = condition,
        formattedDate = TimeUtils.formatDate(date,languageCode),
        timeInMillis = time,
        dateInMillis = date,
        isEnable = isEnable
    )
}

fun AlertModel.toEntity() : Alert{
    return Alert(
        id = id,
        title = title,
        time = timeInMillis,
        date = dateInMillis,
        type = type,
        condition = condition,
        isEnable = isEnable
    )
}