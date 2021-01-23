package com.example.mobiquitytest.ui.home

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkManager
import com.example.mobiquitytest.R
import com.example.mobiquitytest.ui.home.viewmodels.MapViewModel
import com.example.mobiquitytest.utils.ViewModelFactory
import com.example.mobiquitytest.work.GetCurrentWeatherWorker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var pinLocation: AppCompatImageView? = null
    private var etCountry: TextInputEditText? = null
    private var etCity: TextInputEditText? = null
    private lateinit var btnSave: AppCompatButton
    private var mMap: GoogleMap? = null
    private var latLng: LatLng? = null
    private lateinit var zipCode: String
    private val mapViewModel: MapViewModel by lazy {
        val activity = requireNotNull(this) {
            "This is to initialize check if we are accessing viewmodel only after onActivityCreated()"
        }
        ViewModelProvider(this, ViewModelFactory(activity.application))
            .get(MapViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        pinLocation = findViewById(R.id.pin_location)
        etCountry = findViewById(R.id.et_country)
        etCity = findViewById(R.id.et_city)
        btnSave = findViewById(R.id.btn_save)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                    121
                )
                return
            }
        }

        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.map
        ) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnSave.setOnClickListener {
            if (valid()) {
                lifecycleScope.launch {
                    latLng?.let { it1 ->
                        mapViewModel.saveLocationToDB(
                            it1,
                            etCountry?.text.toString(), etCity?.text.toString(),
                            zipCode
                        )
                        lifecycleScope.launch {
                            GetCurrentWeatherWorker.send(this@MapActivity, zipCode)
                            /*val workInfosForUniqueWork = WorkManager.getInstance(this@MapActivity)
                                .getWorkInfosForUniqueWork(zipCode)

                            if (workInfosForUniqueWork.isDone) {

                            }*/
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun valid(): Boolean {
        return true
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        mMap?.isMyLocationEnabled = true
        mMap?.uiSettings?.apply {
            isZoomControlsEnabled = false
            isMyLocationButtonEnabled = true
            isCompassEnabled = true
            isRotateGesturesEnabled = true
            isZoomGesturesEnabled = true
        }
        mMap?.isBuildingsEnabled = false

        mMap?.clear()
        mMap?.setOnCameraIdleListener {
            latLng = mMap?.cameraPosition?.target
            mMap?.clear()
            pinLocation?.visibility = View.VISIBLE
            try {
                getAddressFromLatLong(latLng?.latitude, latLng?.longitude)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun getAddressFromLatLong(lat: Double?, lng: Double?) {
        if (lat != 0.0 && lng != 0.0) {
            val geocoder = Geocoder(this, Locale.getDefault())
            var addresses: List<Address>? = null
            try {
                addresses = geocoder.getFromLocation(lat ?: 0.0, lng ?: 0.0, 1)
            } catch (e: Exception) {
                Timber.e(e)
            }
            try {
                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val countryStr = address.countryName
                    val cityStr = address.locality
                    zipCode = address.postalCode
                    /*val stateStr =
                        if (isStringNotEmpty(address.adminArea)) address.adminArea else ""
                    val zipCodeStr =
                        if (isStringNotEmpty(address.postalCode)) address.postalCode else ""*/
                    Timber.i("countryStr::$countryStr")
                    Timber.i("cityStr::$cityStr")
                    countryStr?.let {
                        etCountry?.setText(countryStr)
                    }
                    cityStr?.let {
                        etCity?.setText(cityStr)
                    }
                }
                System.gc()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}