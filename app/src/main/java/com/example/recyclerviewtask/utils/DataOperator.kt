package com.example.recyclerviewtask.utils

import com.example.recyclerviewtask.utils.DataOperator.allItemsList
import com.example.recyclerviewtask.utils.DataOperator.favouritesList
import com.example.recyclerviewtask.utils.DataOperator.weatherWithSectionsList
import com.example.recyclerviewtask.data.sectionsList
import com.example.recyclerviewtask.data.weatherForecastList
import com.example.recyclerviewtask.model.ItemWeatherForecast
import com.example.recyclerviewtask.model.WeatherForecast

/**
 * Object that does all operations with data.
 * Has [weatherWithSectionsList] that contains all data, including sections.
 * Has [favouritesList] and [allItemsList] for better sorting system.
 *
 * @author Alexander Gorin
 */
object DataOperator {

    private const val FAV_POSITION_ALL = 1
    private const val FAV_POSITION = 0
    private const val SECTION_FAV_POSITION = 0
    private const val SECTION_ALL_POSITION = 1

    val weatherWithSectionsList = arrayListOf<ItemWeatherForecast>()
    private val favouritesList = arrayListOf<WeatherForecast>()
    private val allItemsList = arrayListOf<WeatherForecast>()
    private val indent: Int
        get() = favouritesList.size + sectionsList.size

    init {
        weatherWithSectionsList.apply {
            add(SECTION_FAV_POSITION, sectionsList.first())
            add(SECTION_ALL_POSITION, sectionsList.last())
            val favList = weatherForecastList.filter { it.isFavourite }
            favouritesList.addAll(favList)
            allItemsList.addAll(weatherForecastList)
            addAll(FAV_POSITION_ALL, favouritesList)
            addAll(indent, allItemsList)
        }
    }

    private fun addAllItemsToList() {
        weatherWithSectionsList.apply {
            clear()
            add(SECTION_FAV_POSITION, sectionsList.first())
            add(SECTION_ALL_POSITION, sectionsList.last())
            addAll(FAV_POSITION_ALL, favouritesList)
            addAll(indent, allItemsList)
        }
    }

    fun addToFavourites(position: Int): Int {
        val item = (weatherWithSectionsList[position] as WeatherForecast)
        val allItemsIndex = allItemsList.indexOf(item)
        allItemsList[allItemsIndex].isFavourite = true
        item.isFavourite = true
        favouritesList.add(FAV_POSITION, item)
        weatherWithSectionsList.add(FAV_POSITION_ALL, item)
        return FAV_POSITION_ALL
    }

    fun removeFromFavourites(position: Int): Int {
        val item = (weatherWithSectionsList[position] as WeatherForecast)
        val favIndex = favouritesList.indexOf(item)
        favouritesList.removeAt(favIndex)
        val allItemsIndex = allItemsList.indexOf(item)
        allItemsList[allItemsIndex].isFavourite = false
        item.isFavourite = false
        val removeAtIndex = favIndex + 1
        weatherWithSectionsList.removeAt(removeAtIndex)
        return removeAtIndex
    }

    fun sortNameAsc() {
        favouritesList.sortBy { it.cityName }
        allItemsList.sortBy { it.cityName }
        addAllItemsToList()
    }

    fun sortNameDesc() {
        favouritesList.sortByDescending { it.cityName }
        allItemsList.sortByDescending { it.cityName }
        addAllItemsToList()
    }

    fun sortTempAsc() {
        favouritesList.sortBy { it.weatherTemp }
        allItemsList.sortBy { it.weatherTemp }
        addAllItemsToList()
    }

    fun sortTempDesc() {
        favouritesList.sortByDescending { it.weatherTemp }
        allItemsList.sortByDescending { it.weatherTemp }
        addAllItemsToList()
    }
}

