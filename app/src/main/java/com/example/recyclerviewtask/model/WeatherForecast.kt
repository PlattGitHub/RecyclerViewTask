package com.example.recyclerviewtask.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class to display weather. Has parcelable implementation via Kotlin Extensions.
 *
 * @author Alexander Gorin
 */
@Parcelize
data class WeatherForecast(
    val cityImageUrl: String,
    val cityName: String,
    val info: String,
    val countryName: String,
    val weatherType: WeatherType,
    val weatherTemp: Int,
    var isFavourite: Boolean = false
) : ItemWeatherForecast(false), Parcelable