package com.example.weatherforecast.presentation.favorite

import com.example.weatherforecast.data.local.entity.Weather
import com.example.weatherforecast.presentation.favorite.model.FavoriteItem


fun Weather.toFavUiModel(): FavoriteItem {
    return FavoriteItem(
        id = id ?: 0,
        cityName = cityName,
        description = cityDescription
    )
}