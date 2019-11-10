package com.example.chatterroyale.listItems

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.example.chatterroyale.R

class SettingOption {
    //PROPERTIES
    var optionName : String = ""
    var optionIcon : Drawable? = null

    constructor(name:String, icon: Drawable){
        optionName = name
        optionIcon = icon
    }

    //METHODS

}