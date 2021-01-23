package com.example.mobiquitytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mobiquitytest.ui.home.viewmodels.HomeViewModel
import com.example.mobiquitytest.utils.ViewModelFactory
import com.example.mobiquitytest.work.GetCurrentWeatherWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this) {
            "This is to initialize check if we are accessing viewmodel only after onActivityCreated()"
        }
        ViewModelProvider(this, ViewModelFactory(activity.application))
            .get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_help, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            homeViewModel.getAllCities().map {
                GetCurrentWeatherWorker.send(this@DashboardActivity, it.pinCode)
            }
        }
    }

}