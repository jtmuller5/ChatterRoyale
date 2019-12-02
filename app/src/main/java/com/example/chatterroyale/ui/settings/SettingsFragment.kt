package com.example.chatterroyale.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.SettingsRVAdapter
import com.example.chatterroyale.listItems.SettingOption
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.settings_selection.*

class SettingsFragment : Fragment() {
    var settings = mutableListOf<SettingOption>()
    private lateinit var main: MainActivity
    var isSwiping : Boolean = false
    var startX: Float? = null
    var endX: Float? = null
    var moveX: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //GENERATE SETTING LIST - EACH OPTION IS A DIFFERENT OBJECT
        var scrollColor : SettingOption = SettingOption("Scrolling Background",resources.getDrawable(R.drawable.nav_settings))
        var notifications : SettingOption = SettingOption("Notifications",resources.getDrawable(R.drawable.nav_settings))
        main = requireActivity() as MainActivity
        settings.add(scrollColor)
        settings.add(notifications)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var parentFrag = this
        settingsRecyclerView.layoutManager = LinearLayoutManager(activity)
        settingsRecyclerView.setHasFixedSize(true)
        settingsRecyclerView.adapter = SettingsRVAdapter(settings,parentFrag)
        settingsRecyclerView.setOnTouchListener { v: View, m: MotionEvent ->
            if (m.actionMasked == MotionEvent.ACTION_DOWN && !isSwiping) {
                startX = m.x
                Log.d("Start", startX.toString())
                Log.d("StartSwipe", isSwiping.toString())
                isSwiping = true
            } else if (m.actionMasked == MotionEvent.ACTION_UP && isSwiping) {
                endX = m.x
                Log.d("End", endX.toString())
                isSwiping = false
            } else if (m.actionMasked == MotionEvent.ACTION_MOVE && isSwiping) {
                moveX = m.x
                Log.d("Move", moveX.toString())
                Log.d("MoveSwipe", isSwiping.toString())
                if (startX!!.toFloat().minus(moveX!!.toFloat()) < -main.MyUser.swipe) {
                    main.drawer_layout.openDrawer(GravityCompat.START)
                }
            }
            false
        }
    }
}