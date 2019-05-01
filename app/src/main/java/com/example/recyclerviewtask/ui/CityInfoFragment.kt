package com.example.recyclerviewtask.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.recyclerviewtask.R
import com.example.recyclerviewtask.model.WeatherForecast

/**
 * Simple [Fragment] subclass that displays info about the city.
 *
 * @author Alexander Gorin
 */
class CityInfoFragment : Fragment() {

    private lateinit var cityImageView: ImageView
    private lateinit var cityNameTextView: TextView
    private lateinit var countryNameTextView: TextView
    private lateinit var infoTextView: TextView
    private var item: WeatherForecast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_info, container, false).apply {
            cityImageView = findViewById(R.id.parallaxImageView)
            cityNameTextView = findViewById(R.id.cityNameTextView)
            countryNameTextView = findViewById(R.id.countryNameTextView)
            infoTextView = findViewById(R.id.infoTextView)
        }
        item = arguments?.getParcelable(CITY_ITEM)
        item?.let {
            cityNameTextView.text = it.cityName
            countryNameTextView.text = it.countryName
            infoTextView.text = it.info
            context?.apply {
                Glide.with(this)
                    .load(it.cityImageUrl)
                    .placeholder(R.drawable.ic_location)
                    .into(cityImageView)
            }
        }
        return view
    }

    companion object {
        fun newInstance(item: WeatherForecast): Fragment {
            return CityInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CITY_ITEM, item)
                }
            }
        }

        private const val CITY_ITEM = "CITY_ITEM"
    }
}
