package com.example.mobiquitytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mobiquitytest.databinding.ActivityDashboardBinding
import com.example.mobiquitytest.ui.home.viewmodels.HomeViewModel
import com.example.mobiquitytest.utils.ViewModelFactory
import com.example.mobiquitytest.work.GetCurrentWeatherWorker
import kotlinx.coroutines.launch
import timber.log.Timber

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
        val bind = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)
        Timber.tag("viewmodel name==> : ${homeViewModel.getName()}")

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_help, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bind.navView.setupWithNavController(navController)

        lifecycleScope.launch {
            homeViewModel.getAllCities().map {
                GetCurrentWeatherWorker.send(this@DashboardActivity, it.pinCode)
            }
        }
    }
}