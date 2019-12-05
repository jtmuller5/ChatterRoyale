package com.example.chatterroyale

import android.util.ArrayMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.reflect.Array

class User {
    //PROPERTIES
    var canPost: Boolean = false  //Flag to determine if the user can post again
    var unspentEXP: Double? = null
    var EXP: Double? = null
    var level: Int? = 0
    var user: FirebaseUser? = null
    var uid: String? = null
    var swipe: Int = 250 // Minimum swipe distance in pixels

    var cxArray: ArrayList<String> = arrayListOf("Quality","Applicability","Originality")
    var cxLength: Int = cxArray.size
    var i: Int = 0
    var currChar: String = cxArray.get(i)

    var utilityList: ArrayList<String> = arrayListOf("Legit Utility","Only In Writing","Funny, So Yes","Funny, But No","No Utility")

    //Historical stats
    var numberOfPosts: Int? = null
    var totalUpvotes: Int? = null
    var totalDownvotes: Int? = null
    var givenUpvotes: Int? = null
    var givenDownvotes: Int? = null
    var postsRemoved: Int? = null
    var postsVotedOn:ArrayList<String> = arrayListOf<String>()
    var qualityVotes:ArrayMap<String,Int> = ArrayMap<String,Int>()
    var applicableVotes:ArrayMap<String,Int> = ArrayMap<String,Int>()
    var originalVotes:ArrayMap<String,Int> = ArrayMap<String,Int>()
    var currPostChar:ArrayMap<String,String> = ArrayMap<String,String>() // Store the current characteristic for each vote. If not defined, it is "valuable"

    //CURRENT ROUND LOGISTICS
    var round: Int? = null
    var stage: Int? = null
    var unspentRoundEXP: Int = 0
    var roundEXP: Int = 0
    var stageVotes: Int = 40
    var prompt: String? = null

    constructor()
}

data class UserData (
    var uid: String? = "",
    var totalEntries: Int? = 0
)