package com.example.chatterroyale.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Entry class
@Database(entities = arrayOf(Entry::class), version = 1, exportSchema = false)
public abstract class EntryRoomDatabase : RoomDatabase() {

    //Need an abstract getter for each DAO
    abstract fun entryDao(): EntryDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EntryRoomDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): EntryRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntryRoomDatabase::class.java,
                    "entry_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}