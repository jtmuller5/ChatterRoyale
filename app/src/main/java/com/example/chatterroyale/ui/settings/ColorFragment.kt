package com.example.chatterroyale.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.ColorsRVAdapter
import com.example.chatterroyale.adapters.SettingsRVAdapter
import com.example.chatterroyale.listItems.SettingColor
import com.example.chatterroyale.listItems.SettingOption
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.settings_scroll_background.*

class ColorFragment:Fragment() {
    var colors = mutableListOf<SettingColor>()
    private lateinit var main: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //GENERATE SETTING LIST - EACH OPTION IS A DIFFERENT OBJECT
        var red : SettingColor = SettingColor("Red","#D81B60")
        var green : SettingColor = SettingColor("Green","#00574B")
        var blue : SettingColor = SettingColor("Blue","#85499AD8")

        /*<color name="colorPrimary">#008577</color>
        <color name="colorPrimaryDark">#00574B</color>
        <color name="colorAccent">#D81B60</color>
        <color name="colorScroll">#85499AD8</color>*/

        colors.add(red)
        colors.add(green)
        colors.add(blue)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        main = requireActivity() as MainActivity
        val root = inflater.inflate(R.layout.settings_scroll_background, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorsRecyclerView.layoutManager = LinearLayoutManager(activity)
        colorsRecyclerView.setHasFixedSize(true)
        colorsRecyclerView.adapter = ColorsRVAdapter(colors)
    }

    override fun onResume() {
        super.onResume()
        main.fabOn(false)
    }
}