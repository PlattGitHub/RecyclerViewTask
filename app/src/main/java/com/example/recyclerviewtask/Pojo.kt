package com.example.recyclerviewtask

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

/**
 * Data class to display sections.
 *
 * @author Alexander Gorin
 */
data class Section(val name: String) : ItemWeatherForecast(true)

/**
 * Open class to show different items in RecyclerView.
 *
 * @author Alexander Gorin
 */
open class ItemWeatherForecast(val isSection: Boolean)

/**
 * Enum to specify weather type.
 *
 * @author Alexander Gorin
 */
enum class WeatherType {
    RAINY, THUNDER, CLOUDY, SUNNY
}