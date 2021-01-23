package com.example.mobiquitytest.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobiquitytest.MapActivity
import com.example.mobiquitytest.R
import com.example.mobiquitytest.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var bind: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentHomeBinding.bind(view)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        bind.add.setOnClickListener {
            val intent = Intent(activity, MapActivity::class.java)
            startActivity(intent)
        }
    }
}