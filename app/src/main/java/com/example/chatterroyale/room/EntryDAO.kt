package com.example.chatterroyale.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EntryDAO {
    //Load all entries from the database that have been attributed a specific characteristic
    @Query("SELECT * from entry_table where char=:selectChar")
    fun loadCharVotes(selectChar: String): LiveData<List<Entry>>

    //Load all entries from the database (each is a vote by the user
    @Query("SELECT * from entry_table")
    fun loadAllVotes(): LiveData<List<Entry>>

    //Get the quantity and direction of a value vote for a given entry
    //@Query("SELECT entryID,up,quant from entry_table where entryID=:entryID")
    //fun getValVote(entryID: String): LiveData<List<Entry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entry: Entry)

    @Query("DELETE FROM entry_table")
    suspend fun deleteRoundEntries()
}