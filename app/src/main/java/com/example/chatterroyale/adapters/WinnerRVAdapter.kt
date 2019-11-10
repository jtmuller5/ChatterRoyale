package com.example.chatterroyale.adapters

import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.listItems.ChatterEntry

import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class WinnerRVAdapter(val winningEntries:List<ChatterEntry>) : RecyclerView.Adapter<WinnerRVAdapter.ViewHolder>() {

    //PROPERTIES************************************************************************************

    //METHODS***************************************************************************************

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var submissionTime: TextView = itemView.findViewById(com.example.chatterroyale.R.id.submissionTimeValue)
        var submissionStage: TextView = itemView.findViewById(com.example.chatterroyale.R.id.submissionStageValue)
        var valuePoints: TextView = itemView.findViewById(com.example.chatterroyale.R.id.winningValuePointsValue)

        init {
            itemView.setOnClickListener { v: View  ->
                var position: Int = getAdapterPosition()

                Snackbar.make(v, "Click detected on item $position",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(com.example.chatterroyale.R.layout.winning_entry_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = winningEntries?.get(position)
        var subDate = entry?.sTime
        if(subDate != null) {
            var subTime = formatTime(subDate)
            holder?.submissionTime?.text = subTime
        }
        holder?.submissionStage?.text = entry?.stage.toString()
        holder?.valuePoints?.text = entry?.vp.toString()
    }

    override fun getItemCount(): Int {
        return winningEntries.size
    }

    fun formatTime(date : Date?) : String{
        var sdf= SimpleDateFormat("HH:mm")
        var cal : Calendar = Calendar.getInstance()
        cal?.setTime(date)
        var result = sdf?.format(cal.time)
        return result
    }

}