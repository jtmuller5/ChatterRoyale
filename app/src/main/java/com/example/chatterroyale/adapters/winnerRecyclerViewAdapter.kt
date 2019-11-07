package com.example.chatterroyale.adapters

import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.R
import com.example.chatterroyale.entries.ChatterEntry

import com.google.android.material.snackbar.Snackbar

class winnerRecyclerViewAdapter(val winningEntries:List<ChatterEntry>) : RecyclerView.Adapter<winnerRecyclerViewAdapter.ViewHolder>() {

    //PROPERTIES************************************************************************************

    //METHODS***************************************************************************************

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var submissionTime: TextView = itemView.findViewById(R.id.submissionTimeValue)
        var submissionStage: TextView = itemView.findViewById(R.id.submissionStageValue)
        var valuePoints: TextView = itemView.findViewById(R.id.winningValuePointsValue)

        init {

            itemView.setOnClickListener { v: View  ->
                var position: Int = getAdapterPosition()

                Snackbar.make(v, "Click detected on item $position",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.winning_entry_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        val entry = winningEntries.get(i)
        viewHolder?.submissionTime?.text = entry.sTime.toString()
        viewHolder?.submissionStage?.text = entry.stage.toString()
        viewHolder?.valuePoints?.text = entry.vp.toString()
    }

    override fun getItemCount(): Int {
        return winningEntries.size
    }

}