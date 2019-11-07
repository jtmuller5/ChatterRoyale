package com.example.chatterroyale

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


    constructor(currentTime: Date, user: String, currentRound: String, currentStage: Int,post: String){
        //IMMUTABLE PROPERTIES
        val submissionTime: Date = currentTime
        val submittingUser: String = user
        val roundID: String = currentRound
        val stageID: Int = currentStage
        entryPost = post
    }

    init{
       postChars = entryPost.length
    }
}