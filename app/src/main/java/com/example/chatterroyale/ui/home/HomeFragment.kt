package com.example.chatterroyale.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.WinnerRVAdapter
import com.example.chatterroyale.listItems.ChatterEntry
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_chatter.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.simpleName

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mAdapter: WinnerRVAdapter
    private lateinit var main: MainActivity
    var isSwiping : Boolean = false
    var startX: Float? = null
    var endX: Float? = null
    var moveX: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.findWinningEntries()
        main = requireActivity() as MainActivity
        main.fabOn(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home,container,false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        winnersRecyclerView.layoutManager = LinearLayoutManager(activity)
        winnersRecyclerView.setHasFixedSize(true)
        homeViewModel.winningEntriesList.observe(this, Observer { winningEntriesList ->
            winningEntriesList?.let{
                populateRecyclerView(winningEntriesList)
            }
        })

        winnersRecyclerView.setOnTouchListener { v: View, m: MotionEvent ->
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

    override fun onResume() {
        super.onResume()
        homeViewModel.findWinningEntries()
    }

    fun populateRecyclerView(winningEntriesList: List<ChatterEntry>){
        mAdapter = WinnerRVAdapter(winningEntriesList)
        winnersRecyclerView.adapter = mAdapter
    }

}

