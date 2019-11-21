package com.example.chatterroyale.ui.chatter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.listItems.ChatterEntry
import com.example.chatterroyale.ui.home.HomeViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatterViewModel : ViewModel() {

    private val TAG = ChatterViewModel::class.simpleName
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private var stageRef: Query? = firestoreDB?.collection("entries")?.whereEqualTo("round", 1)?.whereEqualTo("stage", 1)

    //RECYCLER VIEW POPULATION
    var chatterEntriesList: MutableLiveData<List<ChatterEntry>> = MutableLiveData()
    var chatterPost: MutableLiveData<String> = MutableLiveData()

    //TODO: Use LiveData
    fun findChatterEntries(round: Int?, stage : Int?) : LiveData<List<ChatterEntry>> {
        val list = mutableListOf<ChatterEntry>()

        if(stage != 24) {
            stageRef = firestoreDB?.collection("entries")?.whereEqualTo("round", round)?.whereEqualTo("stage", stage)?.orderBy("voteSum", Query.Direction.DESCENDING)
        }
        else if (stage == 24) {
            stageRef = firestoreDB?.collection("entries")?.whereEqualTo("round", round)?.whereEqualTo("finalist",true)
        }

        //Get all entries for the current round
        stageRef?.get()?.addOnSuccessListener { entries ->
            for (entry in entries) {
                list.add(entry.toObject(ChatterEntry::class.java))
                chatterEntriesList.postValue(list)
            }
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

  /*  //TODO: Use LiveData
    fun watchCurrentStage() : LiveData<Double> {

        //Get the current stage from the master/today doc
        var todayRef = firestoreDB?.collection("master")?.document("today")
        todayRef?.addSnapshotListener{snapshot,e->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                stage.postValue(snapshot?.getDouble("stage") as Double)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
        return stage
    }

    fun getCurrentStage() : LiveData<Double> {
        firestoreDB?.collection("master")?.document("today")?.get()?.addOnSuccessListener { current ->
            stage.postValue(current.getDouble("stage") as Double)
        }
            ?.addOnFailureListener { e ->
                Log.e("Failed", e?.toString())
            }
        return stage
    }*/

}