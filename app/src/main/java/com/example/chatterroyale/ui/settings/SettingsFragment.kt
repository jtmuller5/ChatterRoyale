package com.example.chatterroyale.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.SettingsRVAdapter
import com.example.chatterroyale.listItems.SettingOption
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.settings_selection.*

class SettingsFragment : Fragment() {
    var settings = mutableListOf<SettingOption>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //GENERATE SETTING LIST - EACH OPTION IS A DIFFERENT OBJECT
        var scrollColor : SettingOption = SettingOption("Scrolling Background",resources.getDrawable(R.drawable.nav_settings))
        var notifications : SettingOption = SettingOption("Notifications",resources.getDrawable(R.drawable.nav_settings))

        settings.add(scrollColor)
        settings.add(notifications)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsRecyclerView.layoutManager = LinearLayoutManager(activity)
        settingsRecyclerView.setHasFixedSize(true)
        settingsRecyclerView.adapter = SettingsRVAdapter(settings)
    }
}