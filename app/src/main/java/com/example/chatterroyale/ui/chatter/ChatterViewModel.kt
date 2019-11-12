package com.example.chatterroyale.ui.chatter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatterroyale.listItems.ChatterEntry
import com.example.chatterroyale.ui.home.HomeViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ChatterViewModel : ViewModel() {

    private val TAG = ChatterViewModel::class.simpleName
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()

    //RECYCLER VIEW POPULATION
    var chatterEntriesList: MutableLiveData<List<ChatterEntry>> = MutableLiveData()
    var chatterPost: MutableLiveData<String> = MutableLiveData()

    //TODO: Use LiveData
    fun findChatterEntries() : LiveData<List<ChatterEntry>> {
        val list = mutableListOf<ChatterEntry>()

        //Get all entries for the current round
        firestoreDB?.collection("entries")?.whereEqualTo("round",1)?.get()?.addOnSuccessListener { entries ->
            for (entry in entries) {
                list.add(entry.toObject(ChatterEntry::class.java))
            }
            chatterEntriesList.postValue(list)
        }
            ?.addOnFailureListener { e ->
                Log.e("Failed", e.toString())
            }
        return chatterEntriesList
    }

    fun setPost(post: String){
        chatterPost.value = post
    }

    fun getPost(): MutableLiveData<String>{
        return chatterPost
    }

}