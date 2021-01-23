package com.example.mobiquitytest.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobiquitytest.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {
    private lateinit var bind: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentHelpBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val customHtml = "<html><body><h1>Welcome to Weather App</h1>" +
                "<p>This App can show weather forecast of any location of your choice</p>" +
                "<h3>1. Go to home screen and click plus icon</h3><h3>2. Move the pointer to any location of your choice</h3><h3>3. Save the location</h3>" +
                "</body></html>"
        bind.helpPage.loadData(customHtml, "text/html", "UTF-8")
    }
}