package com.example.chatterroyale.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.R
import com.google.android.material.snackbar.Snackbar

class WinningViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var submissionTime: TextView = itemView.findViewById(R.id.submissionTimeValue)
    var submissionStage: TextView = itemView.findViewById(R.id.submissionStageValue)
    var valuePoints: TextView = itemView.findViewById(R.id.winningValuePointsValue)

    init {

        itemView.setOnClickListener { v: View ->
            var position: Int = getAdapterPosition()

            Snackbar.make(v, "Click detected on item $position",
                Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }
}
