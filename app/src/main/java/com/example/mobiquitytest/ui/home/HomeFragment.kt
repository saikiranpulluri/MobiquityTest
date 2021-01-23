package com.example.mobiquitytest.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiquitytest.R
import com.example.mobiquitytest.adapters.CityAdapter
import com.example.mobiquitytest.databinding.FragmentHomeBinding
import com.example.mobiquitytest.ui.home.viewmodels.HomeViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel by activityViewModels<HomeViewModel>()
    private lateinit var bind: FragmentHomeBinding
    private lateinit var cityAdapter: CityAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentHomeBinding.bind(view)/*
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)*/
        cityAdapter = CityAdapter(requireContext())
        bind.add.setOnClickListener {
            val intent = Intent(activity, MapActivity::class.java)
            startActivity(intent)
        }

        bind.savedCities.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            this.adapter = cityAdapter
        }

        lifecycleScope.launch {
            viewModel.getAllCitiesLive().observe(viewLifecycleOwner) {
                it?.let {
                    if (it.isNotEmpty()) {
                        cityAdapter.cities = it
                        bind.hint.visibility = View.GONE
                        bind.savedCities.visibility = View.VISIBLE
                    } else {
                        bind.hint.visibility = View.VISIBLE
                        bind.savedCities.visibility = View.GONE
                    }
                }
            }
        }
    }
}