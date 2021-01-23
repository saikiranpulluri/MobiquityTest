package com.example.mobiquitytest.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.mobiquitytest.databinding.FragmentSettingsBinding
import com.example.mobiquitytest.ui.home.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private lateinit var bind: FragmentSettingsBinding
    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentSettingsBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.view.setOnClickListener {
            lifecycleScope.launch {
                viewModel.resetLocations()
            }
        }
    }
}