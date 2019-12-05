package com.example.chatterroyale.ui.repo

import android.os.Bundle
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
import com.example.chatterroyale.adapters.CategoryRVAdapter
import com.example.chatterroyale.adapters.ChatterRVAdapter
import com.example.chatterroyale.listItems.ChatterEntry
import com.example.chatterroyale.ui.chatter.ChatterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_today.*

class RepoCategory : Fragment() {

    private val TAG = RepoCategory::class.simpleName

    private lateinit var chatterViewModel: ChatterViewModel
    private lateinit var mAdapter: ChatterRVAdapter
    private lateinit var main : MainActivity
    lateinit var layoutManager: LinearLayoutManager
    var isSwiping : Boolean = false
    var startX: Float? = null
    var endX: Float? = null
    var moveX: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatterViewModel = ViewModelProviders.of(this).get(ChatterViewModel::class.java)
        main = requireActivity() as MainActivity
        layoutManager = LinearLayoutManager(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_today,container,false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prompt_value.text = main.MyUser.prompt
        chatterRecyclerView.layoutManager = layoutManager
        chatterRecyclerView.setHasFixedSize(true)
        votingPower.text = main.MyUser.stageVotes.toString()

        chatterViewModel.chatterEntriesList.observe(this, Observer { chatterEntriesList ->
            chatterEntriesList?.let{
                populateRecyclerView(chatterEntriesList)
            }
        })
        swipeContainer.setOnRefreshListener {
            chatterViewModel.findChatterEntries(main.round,main.stage)
            swipeContainer.isRefreshing = false
        }

        chatterRecyclerView.setOnTouchListener { v: View, m: MotionEvent ->
            if (m.actionMasked == MotionEvent.ACTION_DOWN && !isSwiping) {
                startX = m.x
                isSwiping = true
            } else if (m.actionMasked == MotionEvent.ACTION_UP && isSwiping) {
                endX = m.x
                isSwiping = false
            } else if (m.actionMasked == MotionEvent.ACTION_MOVE && isSwiping) {
                moveX = m.x
                if (startX!!.toFloat().minus(moveX!!.toFloat()) < -main.MyUser.swipe) {
                    main.drawer_layout.openDrawer(GravityCompat.START)
                }
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        main.fabOn(true)
        chatterViewModel.findChatterEntries(main.round,main.stage)
        currentStage.text = main.stage.toString()
        votingPower.text = main.MyUser.stageVotes.toString()
    }

    fun populateRecyclerView(chatterEntriesList: List<ChatterEntry>){
        mAdapter = CategoryRVAdapter(chatterEntriesList,main,this)
        chatterRecyclerView.adapter = mAdapter
    }
}