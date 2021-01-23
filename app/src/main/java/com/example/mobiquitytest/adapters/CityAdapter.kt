package com.example.mobiquitytest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiquitytest.R
import com.example.mobiquitytest.database.City
import com.example.mobiquitytest.databinding.ItemMainBinding
import com.example.mobiquitytest.utils.Utils

internal class CityAdapter(private val context: Context) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private lateinit var listener: OnCityClickListener
    var cities: List<City> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind.root)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun setOnCityClickListener(clickListener: OnCityClickListener) {
        listener = clickListener
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private var cityName: AppCompatTextView? = null
        private var temperature: AppCompatTextView? = null
        private var mainView: View? = null

        init {
            cityName = itemView.findViewById(R.id.title)
            temperature = itemView.findViewById(R.id.temperature)
            mainView = itemView.findViewById(R.id.view)
        }

        fun bind(city: City) {
            cityName?.text = city.cityName
            temperature?.text =
                String.format(
                    "%s%s",
                    Utils.convertKelvinToCelsius(city.temperatureInKelvin),
                    context.getString(R.string.centigrade_symbol)
                )

            mainView?.setOnClickListener {
                listener.onCityClicked(city)
            }
        }
    }

    interface OnCityClickListener {
        fun onCityClicked(city: City)
    }
}