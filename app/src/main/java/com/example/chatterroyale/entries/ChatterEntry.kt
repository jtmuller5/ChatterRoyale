package com.example.chatterroyale.entries

import java.util.*

class ChatterEntry {

    //VARIABLE PROPERTIES
    var valuePoints: Int = 0                        //VPs - Value points are used to determine when an entry is eliminated during the reduction phase
    var effectiveValuePoints: Int = 0               //EVPs - These are used to determine the cumulative votes an entry has received
    var alive: Boolean = true                       //Flag determines if an entry is still alive in the current round
    var upvotes: Int = 0
    var downvotes: Int = 0
    var entryPost: String = ""                      //The text post submitted by the user
    var postChars: Int = 0                          //Count the number of characters in a post, including spaces
    var timeAlive: Long = 0

    //IMMUTABLE PROPERTIES DEFINED AT CREATION
    val submissionTime: Date? = null
    val submittingUser: String? = ""
    val roundID: String? = ""
    val stageID: Int = 1

    //IMMUTABLE PROPERTIES DEFINED AT ELIMINATION
    var eliminationTime: Date? = null
    var entryRank: Int? = 0

   /* constructor(currentTime: Date, user: String, currentRound: String, currentStage: Int,post: String){
        //IMMUTABLE PROPERTIES DEFINED AT CREATION
        submissionTime = currentTime
        submittingUser = user
        roundID = currentRound
        stageID= currentStage
        entryPost = post
    }*/

    init{
       postChars = entryPost.length
    }

    //METHODS
    //Set the elimination time when the entry is eliminated by DE or reduction
    fun eliminate(currentTime: Date){
        eliminationTime = currentTime
    }
}