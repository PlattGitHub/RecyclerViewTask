package com.example.recyclerviewtask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.recyclerviewtask.R
import com.example.recyclerviewtask.model.WeatherForecast
import com.example.recyclerviewtask.ui.ViewPagerFragment.ParallaxViewPagerTransformer
import com.example.recyclerviewtask.ui.ViewPagerFragment.WeatherViewPagerAdapter
import com.google.android.material.tabs.TabLayout

/**
 * Simple [Fragment] subclass with [ViewPager].
 * Has inner [WeatherViewPagerAdapter] class to set adapter to [ViewPager].
 * Also has inner [ParallaxViewPagerTransformer] class to set page transformer to [ViewPager].
 *
 * @author Alexander Gorin
 */
class ViewPagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: WeatherViewPagerAdapter
    private var item: WeatherForecast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false).apply {
            viewPager = findViewById(R.id.viewPager)
            tabLayout = findViewById(R.id.tabLayout)
        }

        (activity as? AppCompatActivity)?.supportActionBar?.elevation = TOOLBAR_ELEVATION

        item = arguments?.getParcelable(ITEM_KEY)
        item?.let {
            adapter = WeatherViewPagerAdapter(childFragmentManager)
            adapter.addFragment(CityInfoFragment.newInstance(it), getString(R.string.city_info))
            adapter.addFragment(
                WeatherInfoFragment.newInstance(it),
                getString(R.string.weather_info)
            )
            viewPager.adapter = adapter
            viewPager.setPageTransformer(true, ParallaxViewPagerTransformer())
            tabLayout.setupWithViewPager(viewPager)
        }

        return view
    }

    inner class WeatherViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragmentList = arrayListOf<Fragment>()
        private val fragmentTitleList = arrayListOf<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount() = fragmentList.size

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }
    }

    inner class ParallaxViewPagerTransformer : ViewPager.PageTransformer {

        override fun transformPage(view: View, position: Float) {
            val imageView = view.findViewById<ImageView>(R.id.parallaxImageView)
            val pageWidth = view.width
            when {
                position < -1 -> view.alpha = 1F
                position <= 1 -> imageView.translationX = -position * (pageWidth / 2)
                else -> view.alpha = 1F
            }
        }
    }

    companion object {
        fun newInstance(item: WeatherForecast): Fragment {
            return ViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEM_KEY, item)
                }
            }
        }

        private const val ITEM_KEY = "ITEM_KEY"
        private const val TOOLBAR_ELEVATION = 0F
    }
}