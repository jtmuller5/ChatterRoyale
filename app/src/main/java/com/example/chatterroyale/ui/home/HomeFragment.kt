package com.example.chatterroyale.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.WinnerRecyclerViewAdapter
import com.example.chatterroyale.entries.ChatterEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.simpleName

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mAdapter: WinnerRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.findWinningEntries()
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
    }

    fun populateRecyclerView(winningEntriesList: List<ChatterEntry>){
        mAdapter = WinnerRecyclerViewAdapter(winningEntriesList)
        winnersRecyclerView.adapter = mAdapter
    }

}

