package com.example.chatterroyale.listItems

data class Vote (
    var voteID: String?,
    var entryID: String?,
    var characteristic: String?,
    var up: Boolean?,
    var quantity: Int?
    )

class VoteDetails {
    var voteID: String? = ""
    var entryID: String? = ""
    var characteristic: String? = ""
    var up: Boolean? = false
    var quantity: Int? = 0
}
