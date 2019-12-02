package com.example.chatterroyale.adapters

import android.app.Activity
import android.graphics.PorterDuff
import android.opengl.Visibility
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.example.chatterroyale.listItems.ChatterEntry
import com.example.chatterroyale.listItems.Vote
import com.example.chatterroyale.room.Entry
import com.example.chatterroyale.room.EntryViewModel
import com.example.chatterroyale.ui.chatter.ChatterFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.chatter_entry_card.view.*
import kotlinx.android.synthetic.main.fragment_chatter.*
import java.security.KeyStore
import java.util.zip.Inflater
import kotlin.math.absoluteValue
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider

class ChatterRVAdapter(val chatterEntries:List<ChatterEntry>, main: MainActivity,chatter: ChatterFragment): RecyclerView.Adapter<ChatterRVAdapter.ViewHolder>(){

    //PROPERTIES************************************************************************************
    private var firestoreDB: FirebaseFirestore = FirebaseFirestore.getInstance()
    var entryRef = firestoreDB.collection("entries")
    val userRef = firestoreDB.collection("users")
    var main: MainActivity=main
    var chatter: ChatterFragment=chatter

    //METHODS***************************************************************************************
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var chatterPost: TextView = itemView.findViewById(R.id.chatterPost) as TextView
        var entryID: String = ""
        var doubleUp = itemView.findViewById(R.id.doubleUp) as ImageView
        var singleUp = itemView.findViewById(R.id.singleUp) as ImageView
        var doubleDown = itemView.findViewById(R.id.doubleDown) as ImageView
        var singleDown = itemView.findViewById(R.id.singleDown) as ImageView
        var characteristic = itemView.findViewById(R.id.characteristicVal) as TextView
        var finalistStar = itemView.findViewById(R.id.finalistStar) as ImageView
        var finalist: Boolean = false

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
            var characteristic = itemView.findViewById(R.id.characteristicVal) as TextView
            var finalistStar = itemView.findViewById(R.id.finalistStar) as ImageView
            var chatterPost = itemView.findViewById(R.id.chatterPost) as TextView

            doubleUp.setOnClickListener{ v: View ->
                var charVal:String = characteristic.text.toString()
                var success=vote(true,2,entryID,v,charVal,4)
                if(success){doubleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
            }

            singleUp.setOnClickListener{ v: View ->
                var charVal:String = characteristic.text.toString()
                var success=vote(true,1,entryID,v,charVal,2)
                if(success){singleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
            }
            doubleDown.setOnClickListener{ v: View ->
                var charVal:String = characteristic.text.toString()
                var success=vote(false,2,entryID,v,charVal,2)
                if(success){doubleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
            }

            singleDown.setOnClickListener{ v: View ->
                var charVal:String = characteristic.text.toString()
                var success=vote(false,1,entryID,v,charVal,1)
                if(success){singleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
            }

            characteristic.setOnClickListener{v: View ->
                main.MyUser.i = main.MyUser.cxArray.indexOf(characteristic.text) // Set index to current character...
                main.incrementCxArray() // Then increment

                characteristic.text=main.MyUser.cxArray.get(main.MyUser.i)
                var char = characteristic.text
                var valVote : Int? = 0

                singleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
                doubleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
                singleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
                doubleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)

                if(char=="Valuable"){valVote = main.MyUser.valuableVotes[entryID]}
                if(char=="Intelligent"){valVote = main.MyUser.intelligentVotes[entryID]}
                if(char=="Funny"){valVote = main.MyUser.funnyVotes[entryID]}
                if(char=="Original"){valVote = main.MyUser.originalVotes[entryID]}

                if(valVote==1){singleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
                if(valVote==2){doubleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
                if(valVote==-1){singleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
                if(valVote==-2){doubleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
            }

            chatterPost.setOnClickListener { v: View ->
                var popupmenu:PopupMenu = PopupMenu(v.context,v)
                var inflater: MenuInflater = popupmenu.menuInflater
                inflater.inflate(R.menu.repo_menu,popupmenu.menu)
                main.MyUser.tagList.forEach {
                    popupmenu.menu.add(0, 1, 0, it)
                }
                popupmenu.show()
                popupmenu.setOnMenuItemClickListener { item: MenuItem? ->

                    attributeTag(item?.title.toString(),entryID)

                    true
                }
            }

            finalistStar.visibility = View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.chatter_entry_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = chatterEntries.get(position)
        val entryID = entry.entryID
        var valVote : Int? = 0
        var char = holder.characteristic.text.toString()
        //var loc = chatter.layoutManager.findFirstVisibleItemPosition()+2 //Approximation of center

        holder.singleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
        holder.doubleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
        holder.singleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
        holder.doubleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)

        if(char=="Valuable" && main.MyUser.valuableVotes.contains(entryID)){valVote = main.MyUser.valuableVotes[entryID]}
        if(char=="Intelligent" && main.MyUser.intelligentVotes.contains(entryID)){valVote = main.MyUser.intelligentVotes[entryID]}
        if(char=="Funny" && main.MyUser.funnyVotes.contains(entryID)){valVote = main.MyUser.funnyVotes[entryID]}
        if(char=="Original" && main.MyUser.originalVotes.contains(entryID)){valVote = main.MyUser.originalVotes[entryID]}

        if(valVote==1){holder.singleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
        if(valVote==2){holder.doubleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
        if(valVote==-1){holder.singleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
        if(valVote==-2){holder.doubleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}

        /*if(loc>holder.adapterPosition||loc<holder.adapterPosition){
            holder.characteristic.visibility = View.INVISIBLE
        }*/
        holder.chatterPost.text = entry.entryPost.toString()
        holder.entryID = entry.entryID
        holder.characteristic.text=main.MyUser.currChar
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val entry = chatterEntries.get(holder.adapterPosition)
        val entryID = entry.entryID
        val show = entry.finalist
        var char = holder.characteristic.text.toString()
        var valVote : Int? = 0

        if(char=="Valuable" && main.MyUser.valuableVotes.contains(entryID)){valVote = main.MyUser.valuableVotes[entryID]}
        if(char=="Intelligent" && main.MyUser.intelligentVotes.contains(entryID)){valVote = main.MyUser.intelligentVotes[entryID]}
        if(char=="Funny" && main.MyUser.funnyVotes.contains(entryID)){valVote = main.MyUser.funnyVotes[entryID]}
        if(char=="Original" && main.MyUser.originalVotes.contains(entryID)){valVote = main.MyUser.originalVotes[entryID]}

        if(valVote==1){holder.singleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
        if(valVote==2){holder.doubleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
        if(valVote==-1){holder.singleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}
        if(valVote==-2){holder.doubleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorAccentDark),PorterDuff.Mode.SRC_IN)}

        holder.chatterPost.text = entry.entryPost.toString()
        holder.entryID = entry.entryID
        if(show){ holder.finalistStar.visibility=View.VISIBLE }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.finalistStar.visibility = View.INVISIBLE
        holder.singleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
        holder.doubleUp.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
        holder.singleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
        holder.doubleDown.setColorFilter(ContextCompat.getColor(main,R.color.colorWhite),PorterDuff.Mode.SRC_IN)
    }

    override fun getItemCount(): Int {
        return chatterEntries.size
    }

    /*FUNCTION: Vote
    DEFINITION: Caste a vote for a single post. The post ID is then stored in an array along with the vote quantity
    PARAMETERS:
        up - True if the vote is in the positive direction*/


    fun vote(up: Boolean, num: Int,entryID: String,v: View,char: String, EXP: Int): Boolean{
        val thisEntry = entryRef.document(entryID)                      //Doc ref
        val thisUser = userRef.document(main.MyUser.uid.toString())     //Doc ref
        val userVotes = thisUser.collection("votes")        //Collection ref

        var direction = "Upvotes"
        var quantity = "Single"
        var voters = "upvoters"
        var voteID = userVotes!!.document().id
        var voteQuant = num

        if(voteQuant == 2){
            quantity = "Double"
        }

        if(!up){
            voteQuant=0-num
            direction="Downvotes"
            voters="downvoters"}


        var vote = Vote(voteID,entryID,char,up,voteQuant)
        var voteAdded = false

        if(main.MyUser.stageVotes<=0){
            Snackbar.make(v, "You're out of votes for now",Snackbar.LENGTH_LONG).setAction("Action", null).show()
            voteAdded=false
        }

        else if(((char == "Valuable" && main.MyUser.valuableVotes.contains(entryID))||
                    (char == "Intelligent" && main.MyUser.intelligentVotes.contains(entryID))||
                    (char == "Funny" && main.MyUser.funnyVotes.contains(entryID))||
                    (char == "Original" && main.MyUser.originalVotes.contains(entryID)))){
            Log.d("Already",char)
            Snackbar.make(v, "You already voted on this post",Snackbar.LENGTH_LONG).setAction("Action", null).show()
            voteAdded=false
        }

        //Check to make sure the entry ID is not blank before doing anything
        else if(entryID != "" && ((char == "Valuable" && !main.MyUser.valuableVotes.contains(entryID))||
                    (char == "Intelligent" && !main.MyUser.intelligentVotes.contains(entryID))||
                    (char == "Funny" && !main.MyUser.funnyVotes.contains(entryID))||
                    (char == "Original" && !main.MyUser.originalVotes.contains(entryID)))) {

                firestoreDB.runBatch { batch ->
                    //Update the entry stats
                    batch.update(thisEntry,quantity+direction,FieldValue.increment(1))
                    batch.update(thisEntry,"totalVotes",FieldValue.increment(1))
                    batch.update(thisEntry,"voteSum",FieldValue.increment(1))
                    batch.update(thisEntry,char+quantity+direction,FieldValue.increment(1))
                    batch.update(thisEntry,voters,FieldValue.arrayUnion(main.MyUser.uid))

                    //Update vote data for the user. Each vote has its own record
                    batch.set(userVotes.document(voteID),vote)

                    //Update the user's stats
                    batch.update(thisUser,direction+"Given",FieldValue.increment(1))
                    batch.update(thisUser,"totEXP",FieldValue.increment(EXP.toDouble()))
                    batch.update(thisUser,"unspentEXP",FieldValue.increment(EXP.toDouble()))
                }.addOnCompleteListener{
                    //Upvotes counted
                    main.MyUser.postsVotedOn.add(entryID)

                main.MyUser.roundEXP += EXP
                main.MyUser.unspentRoundEXP += EXP
            }

            if (char=="Valuable") { main.MyUser.valuableVotes.put(entryID, voteQuant) }
            else if (char=="Intelligent") { main.MyUser.intelligentVotes.put(entryID, voteQuant) }
            else if (char=="Funny") { main.MyUser.funnyVotes.put(entryID, voteQuant) }
            else if (char=="Original") { main.MyUser.originalVotes.put(entryID, voteQuant) }

            main.MyUser.stageVotes -= num.absoluteValue
            chatter.votingPower.text = main.MyUser.stageVotes.toString()

            var entry = Entry(entryID,char,up,num)
            main.recordVote().execute(entry)
            voteAdded=true
        }

        return voteAdded

    }

    fun attributeTag(tag:String,entryID: String):Boolean{
        var tagRef = firestoreDB.collection("entries")?.document(entryID)
        tagRef.get()
            ?.addOnSuccessListener {entry ->
                tagRef.update("tags",FieldValue.arrayUnion(tag))
            }
        return true
    }

}