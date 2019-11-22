package com.example.chatterroyale.ui.chatter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.ChatterRVAdapter
import com.example.chatterroyale.listItems.ChatterEntry
import kotlinx.android.synthetic.main.fragment_chatter.*

class ChatterFragment : Fragment() {

    private val TAG = ChatterFragment::class.simpleName

    private lateinit var chatterViewModel: ChatterViewModel
    private lateinit var mAdapter: ChatterRVAdapter
    private lateinit var main : MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatterViewModel = ViewModelProviders.of(this).get(ChatterViewModel::class.java)
        main = requireActivity() as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_chatter,container,false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatterRecyclerView.layoutManager = LinearLayoutManager(activity)
        chatterRecyclerView.setHasFixedSize(true)

        chatterViewModel.chatterEntriesList.observe(this, Observer { chatterEntriesList ->
                chatterEntriesList?.let{
                populateRecyclerView(chatterEntriesList)
            }
        })
        swipeContainer.setOnRefreshListener {
            chatterViewModel.findChatterEntries(main.round,main.stage)
            swipeContainer.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        main.fabOn(false)
        chatterViewModel.findChatterEntries(main.round,main.stage)
        currentStage.text = main.stage.toString()
    }

    fun populateRecyclerView(chatterEntriesList: List<ChatterEntry>){
            mAdapter = ChatterRVAdapter(chatterEntriesList,main)
            chatterRecyclerView.adapter = mAdapter
    }
}