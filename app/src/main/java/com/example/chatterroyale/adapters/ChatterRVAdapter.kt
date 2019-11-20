package com.example.chatterroyale.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.example.chatterroyale.listItems.ChatterEntry
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.chatter_entry_card.view.*

class ChatterRVAdapter(val chatterEntries:List<ChatterEntry>, main: MainActivity): RecyclerView.Adapter<ChatterRVAdapter.ViewHolder>(){

    //PROPERTIES************************************************************************************
    private var firestoreDB: FirebaseFirestore = FirebaseFirestore.getInstance()
    var entryRef = firestoreDB.collection("entries")
    val userRef = firestoreDB.collection("users")
    var main: MainActivity=main

    //METHODS***************************************************************************************
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var chatterPost: TextView = itemView.findViewById(R.id.chatterPost)
        var entryID: String = ""


        init {
            /*itemView.setOnClickListener { v: View ->
                var position: Int = getAdapterPosition()
                var test = v.chatterPost

                Snackbar.make(v, "Click detected on item $position",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }*/
            var doubleUp = itemView.findViewById(R.id.doubleUp) as ImageView
            var singleUp = itemView.findViewById(R.id.singleUp) as ImageView
            var doubleDown = itemView.findViewById(R.id.doubleDown) as ImageView
            var singleDown = itemView.findViewById(R.id.singleDown) as ImageView

            doubleUp.setOnClickListener{ v: View ->
                vote(true,2,entryID,v)
                doubleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)
            }

            singleUp.setOnClickListener{ v: View ->
                vote(true,1,entryID,v)
                singleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)
            }
            doubleDown.setOnClickListener{ v: View ->
                vote(false,2,entryID,v)
                doubleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)
            }

            singleDown.setOnClickListener{ v: View ->
                vote(false,1,entryID,v)
                singleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.chatter_entry_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = chatterEntries.get(position)
        val entryID = entry.entryID

        holder.chatterPost.text = entry.entryPost.toString()
        holder.entryID = entry.entryID
    }

    override fun getItemCount(): Int {
        return chatterEntries.size
    }

    fun vote(up: Boolean, num: Int,entryID: String,v: View){
        val thisEntry = entryRef.document(entryID)
        val thisUser = userRef.document(main.MyUser.uid.toString())

        //Check to make sure the entry ID is not blank before doing anything
        if(entryID != "" && !main.MyUser.postsVotedOn.contains(entryID)) {
            //Handle Upvotes
            if (up) {
                if(num==1){
                        firestoreDB.runBatch { batch ->
                            //Update the entry stats
                            batch.update(thisEntry,"singleUpvotes",FieldValue.increment(1))
                            batch.update(thisEntry,"totalVotes",FieldValue.increment(1))
                            batch.update(thisEntry,"voteSum",FieldValue.increment(1))
                            batch.update(thisEntry,"upvoters",FieldValue.arrayUnion(main.MyUser.uid))

                            //Update the user's stats
                            batch.update(thisUser,"upvotesGiven",FieldValue.increment(1))
                            batch.update(thisUser,"totEXP",FieldValue.increment(2))
                        }.addOnCompleteListener{
                            //Upvotes counted
                            main.MyUser.postsVotedOn.add(entryID)
                    }
                    main.MyUser.roundEXP += 2
                    main.MyUser.unspentRoundEXP += 2
                }
                else if(num==2){
                    firestoreDB.runBatch { batch ->
                        //Update the entry stats
                        batch.update(thisEntry,"doubleUpvotes",FieldValue.increment(1))
                        batch.update(thisEntry,"totalVotes",FieldValue.increment(1))
                        batch.update(thisEntry,"voteSum",FieldValue.increment(2))
                        batch.update(thisEntry,"upvoters",FieldValue.arrayUnion(main.MyUser.uid))

                        //Update the user's stats
                        batch.update(thisUser,"upvotesGiven",FieldValue.increment(2))
                        batch.update(thisUser,"totEXP",FieldValue.increment(4))
                    }.addOnCompleteListener{
                        //Upvotes counted
                        main.MyUser.postsVotedOn.add(entryID)
                    }
                    main.MyUser.roundEXP += 4
                    main.MyUser.unspentRoundEXP += 4
                }
            }
            else {
                if(num==1){
                    firestoreDB.runBatch { batch ->
                        //Update the entry stats
                        batch.update(thisEntry,"singleDownvotes",FieldValue.increment(1))
                        batch.update(thisEntry,"totalVotes",FieldValue.increment(1))
                        batch.update(thisEntry,"voteSum",FieldValue.increment(-1))
                        batch.update(thisEntry,"downvoters",FieldValue.arrayUnion(main.MyUser.uid))

                        //Update the user's stats
                        batch.update(thisUser,"downvotesGiven",FieldValue.increment(1))
                        batch.update(thisUser,"totEXP",FieldValue.increment(1))
                    }.addOnCompleteListener{
                        //Downvotes counted
                        main.MyUser.postsVotedOn.add(entryID)
                    }
                    main.MyUser.roundEXP += 1
                    main.MyUser.unspentRoundEXP += 1
                }
                else if(num==2){
                    firestoreDB.runBatch { batch ->
                        //Update the entry stats
                        batch.update(thisEntry,"doubleDownvotes",FieldValue.increment(1))
                        batch.update(thisEntry,"totalVotes",FieldValue.increment(1))
                        batch.update(thisEntry,"voteSum",FieldValue.increment(-2))
                        batch.update(thisEntry,"downvoters",FieldValue.arrayUnion(main.MyUser.uid))

                        //Update the user's stats
                        batch.update(thisUser,"downvotesGiven",FieldValue.increment(2))
                        batch.update(thisUser,"totEXP",FieldValue.increment(2))
                    }.addOnCompleteListener{
                        //Downvotes counted
                        main.MyUser.postsVotedOn.add(entryID)
                    }
                    main.MyUser.roundEXP += 2
                    main.MyUser.unspentRoundEXP += 2
                }
            }
        }
        else if(main.MyUser.postsVotedOn.contains(entryID)){
            Snackbar.make(v, "You already voted on this post",
                Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }
}