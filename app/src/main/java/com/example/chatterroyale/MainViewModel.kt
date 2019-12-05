package com.example.chatterroyale

import android.util.ArrayMap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatterroyale.listItems.ChatterEntry
import com.example.chatterroyale.listItems.Vote
import com.example.chatterroyale.listItems.VoteDetails
import com.example.chatterroyale.room.Entry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

//ViewModel for storing data that should be available to all fragments
//Primarily will be used for user data
class MainViewModel:ViewModel() {
    private val TAG = MainViewModel::class.simpleName
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()

    var stage: MutableLiveData<Double> = MutableLiveData()
    var round: MutableLiveData<Double> = MutableLiveData()
    var prompt: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<FirebaseUser> = MutableLiveData()
    var roundEXP: MutableLiveData<Int> = MutableLiveData()
    var unspentRoundEXP: MutableLiveData<Int> = MutableLiveData()
    var EXP: MutableLiveData<Double> = MutableLiveData()
    var unspentEXP: MutableLiveData<Double> = MutableLiveData()
    var existingVotes: ArrayList<Entry> = ArrayList<Entry>()
    var qualityVotes: MutableLiveData<ArrayMap<String, Int>> = MutableLiveData()
    var applicableVotes: MutableLiveData<ArrayMap<String, Int>> = MutableLiveData()
    var originalVotes: MutableLiveData<ArrayMap<String, Int>> = MutableLiveData()

    //TODO: Use LiveData
    fun watchCurrentStage() : LiveData<Double> {

        //Get the current stage from the master/today doc
        var todayRef = firestoreDB?.collection("master")?.document("today")
        todayRef?.addSnapshotListener{snapshot,e->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                stage.postValue(snapshot.getDouble("stage") as Double)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
        return stage
    }

    fun watchCurrentRound() : LiveData<Double> {

        //Get the current stage from the master/today doc
        var todayRef = firestoreDB?.collection("master")?.document("today")
        todayRef?.addSnapshotListener{snapshot,e->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                round.postValue(snapshot.getDouble("round") as Double)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
        return round
    }

    fun watchCurrentPrompt() : LiveData<String> {

        //Get the current stage from the master/today doc
        var todayRef = firestoreDB?.collection("master")?.document("today")
        todayRef?.addSnapshotListener{snapshot,e->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                prompt.postValue(snapshot.getString("prompt"))
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
        return prompt
    }

    fun watchCurrentUser() : LiveData<FirebaseUser> {

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            user.postValue(currentUser)
        }
        return user
    }

    fun getOldVotes(uid: String,char: String) : LiveData<ArrayMap<String, Int>>{
        var tempVotes : ArrayMap<String,Int> = ArrayMap()
        var currChar:  MutableLiveData<ArrayMap<String, Int>> = MutableLiveData()
        firestoreDB?.collection("users")?.document(uid)?.collection("votes")?.whereEqualTo("characteristic",char)?.get()
            ?.addOnSuccessListener { votes ->
                for(vote in votes){
                    var voteDetails = vote.toObject(VoteDetails::class.java)
                    tempVotes.put(voteDetails.entryID,voteDetails.quantity)
                }
            }
            ?.addOnFailureListener { e ->
                Log.e("Failed", e.toString())
            }

        if (char == "Quality"){currChar = qualityVotes;qualityVotes.postValue(tempVotes) }
        if (char == "Applicability"){currChar = applicableVotes;applicableVotes.postValue(tempVotes) }
        if (char == "Originality"){currChar = originalVotes;originalVotes.postValue(tempVotes) }

        return currChar
    }

    fun getEXP(uid: String,type : String) : LiveData<Double>{
        var amt :MutableLiveData<Double> = MutableLiveData()
        firestoreDB?.collection("users")?.document(uid)?.get()
            ?.addOnSuccessListener { user ->
                EXP.postValue(user.getDouble("totEXP"))
                unspentEXP.postValue(user.getDouble("unspentEXP"))
            }
            ?.addOnFailureListener { e ->
                Log.e("Failed", e.toString())
            }
        if(type =="total"){ amt = EXP}
        if(type =="unspent"){ amt = unspentEXP }
        return amt
    }

   /* fun watchRoundEXP() : LiveData<Int>{

        return
    }*/
}