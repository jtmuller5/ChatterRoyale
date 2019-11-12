package com.example.chatterroyale.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.listItems.ChatterEntry
import com.google.android.material.snackbar.Snackbar

class ChatterRVAdapter(val chatterEntries:List<ChatterEntry>): RecyclerView.Adapter<ChatterRVAdapter.ViewHolder>(){

    //PROPERTIES************************************************************************************

    //METHODS***************************************************************************************

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var chatterPost: TextView = itemView.findViewById(com.example.chatterroyale.R.id.chatterPost)

        init {
            itemView.setOnClickListener { v: View ->
                var position: Int = getAdapterPosition()

                Snackbar.make(v, "Click detected on item $position",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(com.example.chatterroyale.R.layout.chatter_entry_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = chatterEntries?.get(position)

        holder?.chatterPost?.text = entry?.entryPost.toString()
    }

    override fun getItemCount(): Int {
        return chatterEntries.size
    }
}