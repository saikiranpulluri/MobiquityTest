package com.example.mobiquitytest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiquitytest.R
import com.example.mobiquitytest.databinding.ItemForecastBinding
import com.example.mobiquitytest.network.models.ForecastModel
import com.example.mobiquitytest.utils.Utils
import com.example.mobiquitytest.utils.Utils.dayConverter
import java.util.*

internal class ForecastAdapter(private val context: Context) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    var forecastList: List<ForecastModel.Forecast> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val bind =
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecastList[position])
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }


    inner class ViewHolder(private val view: ItemForecastBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(forecast: ForecastModel.Forecast) {
            view.tvForecastTemp.text = String.format(
                "%s%s",
                Utils.convertKelvinToCelsius(forecast.main?.temp.toString()),
                context.getString(R.string.centigrade_symbol)
            )

            view.tvForecastHumidity.text = String.format(
                "%s %s",
                forecast.main?.humidity,
                context.getString(R.string.percentage_symbol)
            )

            view.tvForecastWind.text = String.format(
                "%s %s",
                forecast.wind?.speed,
                context.getString(R.string.units_wind)
            )

            view.tvForecastTime.text = dayConverter((forecast.dt).toLong())
            view.tvRainCheck.text =
                forecast.weather?.get(0)?.description?.capitalize(Locale.ENGLISH)
        }
    }
}