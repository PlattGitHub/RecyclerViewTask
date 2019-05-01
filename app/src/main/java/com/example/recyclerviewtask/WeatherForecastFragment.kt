package com.example.recyclerviewtask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.recyclerviewtask.WeatherForecastFragment.*
import com.example.recyclerviewtask.WeatherType.*

/**
 * Simple [Fragment] subclass with [RecyclerView].
 * Has inner [WeatherForecastRecyclerViewAdapter] class to set adapter to [RecyclerView].
 * Also has [WeatherForecastViewHolder] and [SectionViewHolder] classes that are ViewHolders for [WeatherForecastRecyclerViewAdapter].
 *
 * @author Alexander Gorin
 */
class WeatherForecastFragment : Fragment() {

    val list: List<ItemWeatherForecast> by lazy { DataOperator.weatherWithSectionsList }
    private lateinit var recyclerViewAdapter: WeatherForecastRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_forecast, container, false).apply {
            recyclerView = findViewById(R.id.recyclerView)
        }

        recyclerViewAdapter = WeatherForecastRecyclerViewAdapter()
        //DataOperator.recyclerViewAdapter = recyclerViewAdapter

        recyclerView.apply {
            layoutManager = LinearLayoutManager(recyclerView.context)
            adapter = recyclerViewAdapter
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_DIALOG) {
                data?.getIntExtra(FavouritesDialog.POSITION_ITEM_KEY, 0)?.let {
                    val remove = data.getBooleanExtra(FavouritesDialog.OPERATION_REMOVE_KEY, false)
                    if (remove) {
                        recyclerViewAdapter.notifyItemRemoved(DataOperator.removeFromFavourites(it))
                    } else {
                        recyclerViewAdapter.notifyItemInserted(DataOperator.addToFavourites(it))
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_weather_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_name_asc -> {
                DataOperator.sortNameAsc()
                recyclerViewAdapter.notifyDataSetChanged()
                return true
            }
            R.id.action_name_desc -> {
                DataOperator.sortNameDesc()
                recyclerViewAdapter.notifyDataSetChanged()
                return true
            }
            R.id.action_temp_asc -> {
                DataOperator.sortTempAsc()
                recyclerViewAdapter.notifyDataSetChanged()
                return true
            }
            R.id.action_temp_desc -> {
                DataOperator.sortTempDesc()
                recyclerViewAdapter.notifyDataSetChanged()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    inner class WeatherForecastRecyclerViewAdapter :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            val myViewHolder: RecyclerView.ViewHolder

            if (viewType == VIEW_ITEM) {

                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_weather, parent, false)
                myViewHolder = WeatherForecastViewHolder(view)

                myViewHolder.itemView.setOnLongClickListener {
                    val position = myViewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        FavouritesDialog.newInstance(
                            (list[myViewHolder.adapterPosition] as WeatherForecast),
                            myViewHolder.adapterPosition
                        ).apply {
                            setTargetFragment(this@WeatherForecastFragment, REQUEST_CODE_DIALOG)
                        }.show(activity?.supportFragmentManager, null)
                        true
                    } else {
                        false
                    }
                }

                myViewHolder.itemView.setOnClickListener {
                    val position = myViewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        activity?.supportFragmentManager?.beginTransaction()?.replace(
                            R.id.fragment_container,
                            ViewPagerFragment.newInstance((list[myViewHolder.adapterPosition] as WeatherForecast))
                        )?.addToBackStack(null)?.commit()
                    }
                }

            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_section, parent, false)
                myViewHolder = SectionViewHolder(view)
            }

            return myViewHolder
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val listItem = list[holder.adapterPosition]
            if (holder is WeatherForecastViewHolder) {
                listItem as WeatherForecast
                holder.cityName.text = listItem.cityName
                holder.info.text = listItem.countryName
                holder.temp.text = String.format(
                    holder.temp.context.getString(R.string.temperature),
                    listItem.weatherTemp
                )
                Glide.with(holder.cityName.context)
                    .load(listItem.cityImageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .transition(DrawableTransitionOptions.withCrossFade(TRANSITION_DURATION))
                    .error(R.drawable.ic_location)
                    .into(holder.cityImage)
                holder.weatherImage.setImageDrawable(
                    when (listItem.weatherType) {
                        SUNNY -> ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.weather_sunny
                        )
                        RAINY -> ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.weather_rainy
                        )
                        THUNDER -> ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.weather_lightning
                        )
                        CLOUDY -> ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.weather_cloudy
                        )
                    }
                )
            } else {
                holder as SectionViewHolder
                listItem as Section
                holder.sectionName.text = listItem.name
            }
        }

        override fun getItemViewType(position: Int) =
            if (list[position].isSection) VIEW_SECTION else VIEW_ITEM
    }

    inner class WeatherForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.textViewCityName)
        val cityImage: ImageView = itemView.findViewById(R.id.imageViewCity)
        val weatherImage: ImageView = itemView.findViewById(R.id.imageViewWeather)
        val info: TextView = itemView.findViewById(R.id.textViewInfo)
        val temp: TextView = itemView.findViewById(R.id.textViewCityTemp)
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionName: TextView = itemView.findViewById(R.id.sectionNameTextView)
    }

    companion object {
        private const val VIEW_ITEM = 1
        private const val VIEW_SECTION = 2
        private const val REQUEST_CODE_DIALOG = 123
        private const val TRANSITION_DURATION = 1000
    }
}


