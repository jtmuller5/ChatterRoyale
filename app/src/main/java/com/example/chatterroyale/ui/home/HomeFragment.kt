package com.example.chatterroyale.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.RecyclerAdapter
import com.example.chatterroyale.entries.ChatterEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_main.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mFirestore: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        initFirestore()
        getWinningEntries()
        return root
    }

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
    }

    private fun getWinningEntries(){
        var winningEntries = mutableListOf<ChatterEntry>()
        var ix: Int = 0
        mFirestore?.collection("winningEntries")?.get()?.addOnSuccessListener {entries ->
            for (entry in entries){
                winningEntries.add(entry.toObject(ChatterEntry::class.java))
            }
                winnersRecyclerView.adapter = RecyclerAdapter(winningEntries)
        }

            ?.addOnFailureListener{e ->
                println("Failed"+e?.toString())
            }

    }
}