package com.example.chatterroyale.ui.perks

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.fragment_store.*
import kotlinx.android.synthetic.main.nav_header_main.*

class StoreFragment : Fragment() {

    private lateinit var perksViewModel: ProfileViewModel
    private lateinit var main: MainActivity
    var isSwiping : Boolean = false
    var startX: Float? = null
    var endX: Float? = null
    var moveX: Float? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        main = requireActivity() as MainActivity
        perksViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_store, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeScroller.setOnTouchListener { v: View, m: MotionEvent ->
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