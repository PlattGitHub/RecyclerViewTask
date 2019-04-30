package com.example.recyclerviewtask


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Simple [Fragment] subclass that displays info about the weather.
 *
 * @author Alexander Gorin
 */
class WeatherInfoFragment : Fragment() {

    lateinit var weatherImageView: ImageView
    lateinit var weatherTempTextView: TextView
    lateinit var weatherTypeTextView: TextView
    var item: WeatherForecast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_info, container, false).apply {
            weatherImageView = findViewById(R.id.parallaxImageView)
            weatherTempTextView = findViewById(R.id.weatherTempTextView)
            weatherTypeTextView = findViewById(R.id.weatherTypeTextView)
        }
        item = arguments?.getParcelable(WEATHER_ITEM)
        item?.let {
            weatherTypeTextView.text = it.weatherType.name
            weatherTempTextView.text =
                String.format(getString(R.string.temperature), it.weatherTemp)
            context?.apply {
                val drawable = when (it.weatherType) {
                    WeatherType.SUNNY ->
                        ContextCompat.getDrawable(this, R.drawable.weather_sunny)
                    WeatherType.RAINY ->
                        ContextCompat.getDrawable(this, R.drawable.weather_rainy)
                    WeatherType.THUNDER ->
                        ContextCompat.getDrawable(this, R.drawable.weather_lightning)
                    WeatherType.CLOUDY ->
                        ContextCompat.getDrawable(this, R.drawable.weather_cloudy)
                }
                weatherImageView.setImageDrawable(drawable)
            }
        }
        return view
    }


    companion object {
        fun newInstance(item: WeatherForecast): Fragment {
            return WeatherInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(WEATHER_ITEM, item)
                }
            }
        }

        private const val WEATHER_ITEM = "WEATHER_ITEM"
    }
}

