package com.example.chatterroyale.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.example.chatterroyale.R
import com.example.chatterroyale.listItems.SettingColor

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.color_selection.view.*


class ColorsRVAdapter(val settingColors:List<SettingColor>) : RecyclerView.Adapter<ColorsRVAdapter.ViewHolder>() {

    //PROPERTIES************************************************************************************

    //METHODS***************************************************************************************

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var colorName : TextView = itemView.findViewById(R.id.colorName)
        var colorVal : ImageView = itemView.findViewById(R.id.colorVal)

        init {
            itemView.setOnClickListener { v: View  ->
                var color: String = v.colorName.text.toString()

                Snackbar.make(v, "$color selected",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.example.chatterroyale.R.layout.settings_selection, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = settingColors?.get(position)
        holder?.colorName?.text = option.colorName
        holder?.colorVal?.setBackgroundColor(Color.parseColor(option.colorVal))
    }

    override fun getItemCount(): Int {
        return settingColors.size
    }

}