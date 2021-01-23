package com.example.mobiquitytest.ui.forecast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiquitytest.R
import com.example.mobiquitytest.adapters.ForecastAdapter
import com.example.mobiquitytest.database.City
import com.example.mobiquitytest.databinding.ActivityForecastBinding
import com.example.mobiquitytest.utils.ViewModelFactory
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.launch

class ForecastActivity : AppCompatActivity() {
    private lateinit var forecastAdapter: ForecastAdapter

    private val forecastViewModel: ForecastViewModel by lazy {
        val activity = requireNotNull(this) {
            "This is to initialize check if we are accessing viewmodel only after onActivityCreated()"
        }
        ViewModelProvider(this, ViewModelFactory(activity.application))
            .get(ForecastViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        forecastAdapter = ForecastAdapter(this)
        val city = intent.getSerializableExtra("city") as City
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = city.cityName
        val foreCastList = findViewById<RecyclerView>(R.id.forecast_list)

        foreCastList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.adapter = forecastAdapter
        }

        lifecycleScope.launch {
            val forecastForCity = forecastViewModel.getForecastForCity(city)
            forecastForCity?.list?.let {
                forecastAdapter.forecastList = it
                bind.content.fiveDaysLoading.visibility = View.GONE
            }
        }
    }
}