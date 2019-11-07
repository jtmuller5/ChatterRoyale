package com.example.chatterroyale.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.winnerRecyclerViewAdapter
import com.example.chatterroyale.entries.ChatterEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var mFirestore: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        initFirestore()
        getWinningEntries()
    }

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
    }

    private fun getWinningEntries(){
        var winningEntries = mutableListOf<ChatterEntry>()
        mFirestore?.collection("winningEntries")?.get()?.addOnSuccessListener {entries ->
            for (entry in entries){
                winningEntries.add(entry.toObject(ChatterEntry::class.java))
            }
                winnersRecyclerView.adapter = winnerRecyclerViewAdapter(winningEntries)
        }

            ?.addOnFailureListener{e ->
                println("Failed"+e?.toString())
            }

    }
}