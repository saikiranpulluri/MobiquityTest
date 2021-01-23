package com.example.mobiquitytest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiquitytest.R
import com.example.mobiquitytest.database.City
import com.example.mobiquitytest.utils.Utils

internal class CityAdapter(private val context: Context) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    var cities: List<City> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val rowViewObj: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(rowViewObj)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(cities[position])
        holder.itemView.setOnClickListener {
            // listener.onWifiItemClicked(cities[position])
        }
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    /*   fun setWifiItemClickListener(clickListener: WifiClickListener) {
           listener = clickListener
       }*/

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private var cityName: AppCompatTextView? = null
        private var temperature: AppCompatTextView? = null

        init {
            cityName = itemView.findViewById(R.id.title)
            temperature = itemView.findViewById(R.id.temperature)
        }

        fun bind(city: City) {
            cityName?.text = city.cityName
            temperature?.text =
                String.format(
                    "%s%s",
                    Utils.convertKelvinToCelsius(city.temperatureInKelvin),
                    context.getString(R.string.centigrade_symbol)
                )
        }
    }
}