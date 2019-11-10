package com.example.chatterroyale.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.ColorsRVAdapter
import com.example.chatterroyale.adapters.SettingsRVAdapter
import com.example.chatterroyale.listItems.SettingColor
import com.example.chatterroyale.listItems.SettingOption
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.settings_scroll_background.*

class ColorFragment:Fragment() {
    var colors = mutableListOf<SettingColor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //GENERATE SETTING LIST - EACH OPTION IS A DIFFERENT OBJECT
        var red : SettingColor = SettingColor("Red")
        var green : SettingColor = SettingColor("Green")
        var blue : SettingColor = SettingColor("Blue")

        colors.add(red)
        colors.add(green)
        colors.add(blue)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.settings_scroll_background, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorsRecyclerView.layoutManager = LinearLayoutManager(activity)
        colorsRecyclerView.setHasFixedSize(true)
        colorsRecyclerView.adapter = ColorsRVAdapter(colors)
    }
}