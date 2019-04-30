package com.example.recyclerviewtask

/**
 * Object that does all operations with data.
 * Has recycler's view adapter to notify about changes.
 * Has [weatherWithSectionsList] that contains all data, including sections.
 * Has [favouritesList] and [notFavouritesList] for better sorting system.
 *
 * @author Alexander Gorin
 */
object DataOperator {

    lateinit var recyclerViewAdapter: WeatherForecastFragment.WeatherForecastRecyclerViewAdapter
    val weatherWithSectionsList = arrayListOf<ItemWeatherForecast>()
    private val favouritesList = arrayListOf<WeatherForecast>()
    private val notFavouritesList = arrayListOf<WeatherForecast>()
    private val indent: Int
        get() = favouritesList.size + sectionsList.size

    init {
        weatherWithSectionsList.apply {
            add(0, sectionsList[0])
            add(1, sectionsList[1])
            val favList = weatherForecastList.filter { it.isFavourite }
            val notFavList = weatherForecastList.filter { !it.isFavourite }
            favouritesList.addAll(favList)
            notFavouritesList.addAll(notFavList)
            addAll(1, favouritesList)
            addAll(indent, notFavouritesList)
        }
    }

    private fun addAllItemsToList() {
        weatherWithSectionsList.apply {
            clear()
            add(0, sectionsList[0])
            add(1, sectionsList[1])
            addAll(1, favouritesList)
            addAll(indent, notFavouritesList)
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }

    fun addToFavourites(position: Int) {
        val item = (weatherWithSectionsList.removeAt(position) as WeatherForecast)
        notFavouritesList.remove(item)
        favouritesList.add(0, item.apply {
            isFavourite = true
        })
        weatherWithSectionsList.add(1, item)
        recyclerViewAdapter.notifyItemMoved(position, 1)
    }

    fun removeFromFavourites(position: Int) {
        val item = (weatherWithSectionsList.removeAt(position) as WeatherForecast)
        favouritesList.remove(item)
        notFavouritesList.add(0, item.apply {
            isFavourite = false
        })
        weatherWithSectionsList.add(indent, item)
        recyclerViewAdapter.notifyItemMoved(position, indent)
    }

    fun sortNameAsc() {
        favouritesList.sortBy { it.cityName }
        notFavouritesList.sortBy { it.cityName }
        addAllItemsToList()
    }

    fun sortNameDesc() {
        favouritesList.sortByDescending { it.cityName }
        notFavouritesList.sortByDescending { it.cityName }
        addAllItemsToList()
    }

    fun sortTempAsc() {
        favouritesList.sortBy { it.weatherTemp }
        notFavouritesList.sortBy { it.weatherTemp }
        addAllItemsToList()
    }

    fun sortTempDesc() {
        favouritesList.sortByDescending { it.weatherTemp }
        notFavouritesList.sortByDescending { it.weatherTemp }
        addAllItemsToList()
    }
}

