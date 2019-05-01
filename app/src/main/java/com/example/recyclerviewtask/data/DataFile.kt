package com.example.recyclerviewtask.data

import com.example.recyclerviewtask.model.Section
import com.example.recyclerviewtask.model.WeatherForecast
import com.example.recyclerviewtask.model.WeatherType


/**
 * Data for RecyclerView.
 *
 * @author Alexander Gorin
 */
val weatherForecastList = arrayListOf(
    WeatherForecast(
        "https://avatars.mds.yandex.net/get-pdb/202366/8df7c804-8e4d-49b5-8fef-13095b0ae1ae/s280",
        "Washington, D.C.",
        "Washington, D.C., formally the District of Columbia and commonly referred to as Washington or D.C., is the capital of the United States.",
        "USA",
        WeatherType.CLOUDY,
        15
    ),
    WeatherForecast(
        "https://c.wallhere.com/photos/67/2b/New_York_City_skyscraper_cityscape_building-245770.jpg!s",
        "New York City",
        "The City of New York, usually called either New York City (NYC) or simply New York (NY), is the most populous city in the United States.",
        "USA",
        WeatherType.CLOUDY,
        14
    ),
    WeatherForecast(
        "https://avatars.mds.yandex.net/get-pdb/877347/de8f8e91-b3c0-48fa-8b28-6feb83396737/s280",
        "Minsk",
        "Minsk is the capital and largest city of Belarus, situated on the Svislač and the Nyamiha Rivers.",
        "Belarus",
        WeatherType.THUNDER,
        20
    ),
    WeatherForecast(
        "https://i.pinimg.com/236x/fe/58/54/fe5854f4c2d93421e971aa36f78b6753--wallpaper-architecture-architecture-interiors.jpg",
        "Moscow",
        "Moscow is the capital and most populous city of Russia, with 13.2 million residents within the city limits, 17 million within the urban area and 20 million within the metropolitan area.",
        "Russia",
        WeatherType.RAINY,
        17
    ),
    WeatherForecast(
        "https://avatars.mds.yandex.net/get-pdb/1378219/9d6af057-e791-4b74-9b9a-1d9851c1b8df/s320",
        "Lisbon",
        "Lisbon is the capital and the largest city of Portugal, with an estimated population of 505,526  within its administrative limits in an area of 100.05 km2.",
        "Portugal",
        WeatherType.SUNNY,
        29,
        isFavourite = true
    ),
    WeatherForecast(
        "http://postervdom.ru/upload/shop_3/8/4/3/item_8439/small_item_8439.jpg",
        "Florence",
        "Florence is the capital city of the Italian region of Tuscany. It is the most populous city in Tuscany, with 383,084 inhabitants in 2013, and over 1,520,000 in its metropolitan area.",
        "Italy",
        WeatherType.SUNNY,
        25
    ),
    WeatherForecast(
        "https://studyabroad.washington.edu/_customtags/ct_Image.cfm?Image_ID=52887",
        "Buenos Aires",
        "Buenos Aires is the capital and largest city of Argentina. The city is located on the western shore of the estuary of the Río de la Plata, on the South American continent's southeastern coast.",
        "Argentina",
        WeatherType.SUNNY,
        24,
        isFavourite = true
    ),
    WeatherForecast(
        "https://tr.toluna.com/dpolls_images/2017/08/30/62f07f61-6179-4a55-83dc-a8bf6f6a7c1a_x400.jpg",
        "Tokio",
        "Tokyo, officially Tokyo Metropolis, one of the 47 prefectures of Japan, has served as the Japanese capital since 1869.",
        "Japan",
        WeatherType.RAINY,
        22
    )
)

/**
 * Sections for RecyclerView.
 *
 * @author Alexander Gorin
 */
val sectionsList = arrayListOf(
    Section("Favourites"),
    Section("All")
)