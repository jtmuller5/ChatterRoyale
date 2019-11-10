package com.example.chatterroyale.adapters

import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.R
import com.example.chatterroyale.listItems.SettingOption

import com.google.android.material.snackbar.Snackbar


class SettingsRVAdapter(val settingOptions:List<SettingOption>) : RecyclerView.Adapter<SettingsRVAdapter.ViewHolder>() {

    //PROPERTIES************************************************************************************

    //METHODS***************************************************************************************

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var settingName: TextView = itemView.findViewById(com.example.chatterroyale.R.id.settingName)
        var settingIcon: ImageView = itemView.findViewById(R.id.settingIcon)

        init {
            itemView.setOnClickListener { v: View  ->
                var position: Int = getAdapterPosition()

                Snackbar.make(v, "Click detected on item $position",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.example.chatterroyale.R.layout.settings_selection, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = settingOptions?.get(position)
        holder?.settingName?.text = option.optionName
        holder?.settingIcon?.setImageDrawable(option.optionIcon)
    }

    override fun getItemCount(): Int {
        return settingOptions.size
    }

}