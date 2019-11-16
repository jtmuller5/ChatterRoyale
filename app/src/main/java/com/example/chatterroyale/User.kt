package com.example.chatterroyale

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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

    //CURRENT ROUND LOGISTICS
    var stage: Int? = null

    constructor()
}

data class UserData (
    var uid: String? = "",
    var totalEntries: Int? = 0
)