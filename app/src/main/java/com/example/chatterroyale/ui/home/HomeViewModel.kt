package com.example.chatterroyale.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatterroyale.entries.ChatterEntry
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class HomeViewModel : ViewModel() {

    private val TAG = HomeViewModel::class.simpleName
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()

    //RECYCLER VIEW POPULATION
    var winningEntriesList: MutableLiveData<List<ChatterEntry>> = MutableLiveData()

    //TODO: Use LiveData
    fun findWinningEntries() : LiveData<List<ChatterEntry>> {
        val list = mutableListOf<ChatterEntry>()
        firestoreDB?.collection("winningEntries")?.get()?.addOnSuccessListener { entries ->
            for (entry in entries) {
                list.add(entry.toObject(ChatterEntry::class.java))
            }
            winningEntriesList.postValue(list)
        }
            ?.addOnFailureListener { e ->
                Log.e("Failed", e?.toString())
            }
        return winningEntriesList
    }
}
