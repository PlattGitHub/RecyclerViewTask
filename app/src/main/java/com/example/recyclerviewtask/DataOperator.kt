package com.example.recyclerviewtask

import com.example.recyclerviewtask.DataOperator.allItemsList
import com.example.recyclerviewtask.DataOperator.favouritesList
import com.example.recyclerviewtask.DataOperator.weatherWithSectionsList

/**
 * Object that does all operations with data.
 * Has recycler's view adapter to notify about changes.
 * Has [weatherWithSectionsList] that contains all data, including sections.
 * Has [favouritesList] and [allItemsList] for better sorting system.
 *
 * @author Alexander Gorin
 */
object DataOperator {

    lateinit var recyclerViewAdapter: WeatherForecastFragment.WeatherForecastRecyclerViewAdapter
    val weatherWithSectionsList = arrayListOf<ItemWeatherForecast>()
    private val favouritesList = arrayListOf<WeatherForecast>()
    private val allItemsList = arrayListOf<WeatherForecast>()
    private val indent: Int
        get() = favouritesList.size + sectionsList.size

    init {
        weatherWithSectionsList.apply {
            add(0, sectionsList[0])
            add(1, sectionsList[1])
            val favList = weatherForecastList.filter { it.isFavourite }
            favouritesList.addAll(favList)
            allItemsList.addAll(weatherForecastList)
            addAll(1, favouritesList)
            addAll(indent, allItemsList)
        }
    }

    private fun addAllItemsToList() {
        weatherWithSectionsList.apply {
            clear()
            add(0, sectionsList[0])
            add(1, sectionsList[1])
            addAll(1, favouritesList)
            addAll(indent, allItemsList)
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }

    fun addToFavourites(position: Int) {
        val item = (weatherWithSectionsList[position] as WeatherForecast)
        val allItemsIndex = allItemsList.indexOf(item)
        allItemsList[allItemsIndex].isFavourite = true
        favouritesList.add(0, item.apply {
            isFavourite = true
        })
        weatherWithSectionsList.add(1, item)
        recyclerViewAdapter.notifyItemInserted(1)
    }

    fun removeFromFavourites(position: Int) {
        val item = (weatherWithSectionsList[position] as WeatherForecast)
        val favIndex = favouritesList.indexOf(item)
        favouritesList.removeAt(favIndex)
        val allItemsIndex = allItemsList.indexOf(item)
        allItemsList[allItemsIndex].isFavourite = false
        item.isFavourite = false
        weatherWithSectionsList.removeAt(favIndex + 1)
        recyclerViewAdapter.notifyItemRemoved(favIndex + 1)
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

