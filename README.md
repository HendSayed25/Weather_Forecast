# 🌦️ Weather Forecast Application

## 📌 Project Overview

Weather Forecast Application is an Android mobile application that allows users to check real-time weather conditions using their current location or any selected location on the map.

The application provides detailed weather information such as temperature, humidity, wind speed, pressure, and weather description.
Users can also save favorite locations and set weather alerts for different conditions like rain, snow, fog, and extreme temperatures.

The application is built using **MVVM Architecture** and modern Android development tools including **Jetpack Compose** for building the UI.

---

# ✨ Features

## 🏠 Home Screen

Displays detailed weather information for the selected location:

* Current temperature
* Current date and time
* Humidity
* Wind speed
* Atmospheric pressure
* Cloud coverage
* City name
* Weather icon
* Weather description (e.g., Clear Sky, Light Rain)
* Hourly forecast for the current day
* 5-day weather forecast

---

## ⭐ Favorite Locations

Users can save multiple locations to quickly check their weather.

Features include:

* Add a location using the **map**
* Search for a city using **auto-complete search**
* View forecast for saved locations
* Delete locations from the favorites list

---

## ⚠️ Weather Alerts

Users can create alerts for specific weather conditions.

Alert settings include:

* Alert duration
* Alert type:

    * Notification
    * Alarm sound
* Option to stop or disable alerts

Alerts can be triggered for conditions such as:

* Rain
* Snow
* Fog
* Strong wind
* Very high temperature
* Very low temperature

---

## ⚙️ Settings Screen

Users can customize application preferences.

### 📍 Location

* Get location using **GPS**
* Select location manually from **Map**

### 🌡 Temperature Units

* Celsius
* Kelvin
* Fahrenheit

### 💨 Wind Speed Units

* Meter / Second
* Miles / Hour

### 🌐 Language

* English
* Arabic

---

# 🏗️ Architecture

The project follows **MVVM (Model – View – ViewModel)** architecture to maintain separation of concerns and improve testability.

### Layers

**UI Layer**

* Built using Jetpack Compose
* State-driven UI

**ViewModel Layer**

* Handles UI logic
* Exposes UI states

**Repository Layer**

* Manages data flow between data sources

**Data Layer**

* Remote Data Source (API)
* Local Data Source (Room Database)

---

# 🛠️ Technologies & Tools

* Kotlin
* Jetpack Compose
* MVVM Architecture
* Retrofit
* Room Database
* Coroutines
* WorkManager
* AlarmManager
* MapLibre for map
* Location Services (GPS)
* Unit Testing

---

# 🌐 Weather API :

Weather data is retrieved using the OpenWeatherMap API:

https://api.openweathermap.org/data/2.5/forecast

The API provides:

* Hourly forecast
* 5-day forecast
* Weather conditions and descriptions

# Map Library :
https://maplibre.org/maplibre-compose/getting-started/

