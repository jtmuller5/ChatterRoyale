package com.example.chatterroyale

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.reflect.Array

class User {
    //PROPERTIES
    var canPost: Boolean = false  //Flag to determine if the user can post again
    var spendableEXP: Int? = 0
    var EXP: Int? = null
    var level: Int? = 0
    var user: FirebaseUser? = null
    var uid: String? = null

    //Historical stats
    var numberOfPosts: Int? = null
    var totalUpvotes: Int? = null
    var totalDownvotes: Int? = null
    var givenUpvotes: Int? = null
    var givenDownvotes: Int? = null
    var postsRemoved: Int? = null
    var postsVotedOn:ArrayList<String> = arrayListOf<String>()

    //CURRENT ROUND LOGISTICS
    var round: Int? = null
    var stage: Int? = null
    var unspentRoundEXP: Int = 0
    var roundEXP: Int = 0

    constructor()
}

data class UserData (
    var uid: String? = "",
    var totalEntries: Int? = 0
)