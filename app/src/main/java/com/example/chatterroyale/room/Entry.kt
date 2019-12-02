package com.example.chatterroyale.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.reflect.Constructor

@Entity(tableName = "entry_table")
data class Entry(
    @PrimaryKey @ColumnInfo(name = "entryID") val entryID: String,
    @ColumnInfo(name = "char") val characteristic: String?,
    @ColumnInfo(name = "up") val up: Boolean?,
    @ColumnInfo(name = "quant") val quantity: Int?
)