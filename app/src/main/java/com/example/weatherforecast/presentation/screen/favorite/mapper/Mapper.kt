package com.example.weatherforecast.presentation.screen.favorite.mapper

import com.example.weatherforecast.data.local.entity.Weather
import com.example.weatherforecast.presentation.screen.favorite.model.FavoriteItem


fun Weather.toFavUiModel(): FavoriteItem {
    return FavoriteItem(
        id = id ?: 0,
        cityName = cityName,
        description = cityDescription
    )
}