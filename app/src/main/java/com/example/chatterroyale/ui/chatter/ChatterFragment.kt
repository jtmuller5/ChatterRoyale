package com.example.chatterroyale.ui.chatter

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
import com.example.chatterroyale.adapters.ChatterRVAdapter
import com.example.chatterroyale.adapters.WinnerRVAdapter
import com.example.chatterroyale.listItems.ChatterEntry
import com.example.chatterroyale.ui.home.HomeFragment
import com.example.chatterroyale.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_chatter.*
import kotlinx.android.synthetic.main.fragment_home.*

class ChatterFragment : Fragment() {

    private val TAG = ChatterFragment::class.simpleName

    private lateinit var chatterViewModel: ChatterViewModel
    private lateinit var mAdapter: ChatterRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatterViewModel = ViewModelProviders.of(this).get(ChatterViewModel::class.java)
        chatterViewModel.findChatterEntries()
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
    }

    fun populateRecyclerView(chatterEntriesList: List<ChatterEntry>){
        mAdapter = ChatterRVAdapter(chatterEntriesList)
        chatterRecyclerView.adapter = mAdapter
    }
}