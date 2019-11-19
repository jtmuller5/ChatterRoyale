package com.example.chatterroyale.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.R
import com.example.chatterroyale.listItems.ChatterEntry
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.chatter_entry_card.view.*

class ChatterRVAdapter(val chatterEntries:List<ChatterEntry>): RecyclerView.Adapter<ChatterRVAdapter.ViewHolder>(){

    //PROPERTIES************************************************************************************
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()
    var entryRef = firestoreDB?.collection("entries")

    //METHODS***************************************************************************************
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var chatterPost: TextView = itemView.findViewById(R.id.chatterPost)


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
            var singledown = itemView.findViewById(R.id.doubleUp) as ImageView

            doubleUp.setOnClickListener{ v: View ->
                vote(true,2)
            }

            singleUp.setOnClickListener{ v: View ->
                Snackbar.make(v, "Click detected on single up",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.chatter_entry_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = chatterEntries?.get(position)

        holder?.chatterPost?.text = entry?.entryPost.toString()
    }

    override fun getItemCount(): Int {
        return chatterEntries.size
    }

    fun vote(up: Boolean, num: Int){
        if(up){
            entryRef?.document()
        }
    }
}