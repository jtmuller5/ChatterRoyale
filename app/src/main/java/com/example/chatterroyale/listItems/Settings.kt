package com.example.chatterroyale.listItems

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import com.example.chatterroyale.R

class SettingOption {
    //PROPERTIES
    var optionName : String = ""
    var optionIcon : Drawable? = null

    constructor(name:String, icon: Drawable){
        optionName = name
        optionIcon = icon
    }
}

class SettingColor {
    //PROPERTIES
    var colorName : String = ""
    //var colorVal = getColor(Resources.getSystem(),R.color.colorScroll,null)

    constructor(name:String){
        colorName = name
        //colorVal=color
    }
}