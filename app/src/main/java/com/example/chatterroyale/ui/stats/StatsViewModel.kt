package com.example.chatterroyale.ui.stats

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.ui.perks.StoreViewModel
import com.google.firebase.firestore.FirebaseFirestore

class StatsViewModel : ViewModel() {

    private val TAG = StoreViewModel::class.simpleName
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()

    //Stats
    var totEXP: MutableLiveData<Double> = MutableLiveData()
    var unspentEXP: MutableLiveData<Double> = MutableLiveData()
    var roundEXP: MutableLiveData<Int> = MutableLiveData()
    var unspentRoundEXP: MutableLiveData<Int> = MutableLiveData()
    var upvotesReceived: MutableLiveData<Double> = MutableLiveData()
    var upvotesGiven: MutableLiveData<Double> = MutableLiveData()
    var downvotesReceived: MutableLiveData<Double> = MutableLiveData()
    var downvotesGiven: MutableLiveData<Double> = MutableLiveData()
    var eliminations: MutableLiveData<Double> = MutableLiveData()
    var tagsReceived: MutableLiveData<Double> = MutableLiveData()
    var tagsGiven: MutableLiveData<Double> = MutableLiveData()
    var topTagReceived: MutableLiveData<String> = MutableLiveData()
    var topTagGiven: MutableLiveData<String> = MutableLiveData()
    var totEntries: MutableLiveData<Double> = MutableLiveData()
    var winningEntries: MutableLiveData<Double> = MutableLiveData()
    var finalistEntries: MutableLiveData<Double> = MutableLiveData()
    var eliminatedEntries: MutableLiveData<Double> = MutableLiveData()
    var averageRank: MutableLiveData<Double> = MutableLiveData()
    var averagePostLength: MutableLiveData<Double> = MutableLiveData()

    var main: MainActivity = MainActivity()

    //TODO: Use LiveData
    fun findUserStats(uid: String):LiveData<Double>{
        var statsRef=firestoreDB?.collection("users")?.document(uid)
        statsRef?.addSnapshotListener{ user,e ->
            Log.d("IN",uid.toString())
                if(e!=null){
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if(user != null && user.exists()) {
                    totEXP.postValue(user.getDouble("totEXP"))
                    unspentEXP.value = (user.getDouble("unspentEXP"))
                    //roundEXP.postValue(user.getDouble("roundEXP"))
                    //unspentRoundEXP.postValue(user.getDouble("unspentRoundEXP"))
                    upvotesReceived.postValue(user.getDouble("upvotesReceived"))
                    upvotesGiven.postValue(user.getDouble("upvotesGiven"))
                    downvotesReceived.postValue(user.getDouble("downvotesReceived"))
                    downvotesGiven.postValue(user.getDouble("downvotesGiven"))
                    eliminations.postValue(user.getDouble("eliminations"))
                    tagsReceived.postValue(user.getDouble("tagsReceived"))
                    tagsGiven.postValue(user.getDouble("tagsGiven"))
                    topTagReceived.postValue(user.getString("topTagReceived"))
                    topTagGiven.postValue(user.getString("topTagGiven"))
                    totEntries.postValue(user.getDouble("totalEntries"))
                    winningEntries.postValue(user.getDouble("winningEntries"))
                    finalistEntries.postValue(user.getDouble("finalistEntries"))
                    eliminatedEntries.postValue(user.getDouble("eliminatedEntries"))
                    averageRank.postValue(user.getDouble("averageRank"))
                    averagePostLength.postValue(user.getDouble("averagePostLength"))
                }else {
                    Log.d(TAG, "Current data: null")
                }
            }
        return totEXP
    }

    fun getRoundEXP(): LiveData<Int>{
        roundEXP.value=(main.MyUser.roundEXP)
        return roundEXP
    }

    fun getUnspentRoundEXP(): LiveData<Int>{
        unspentRoundEXP.value=(main.MyUser.unspentRoundEXP)
        return unspentRoundEXP
    }
}