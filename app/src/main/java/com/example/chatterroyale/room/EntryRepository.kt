package com.example.chatterroyale.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class EntryRepository(private val entryDao: EntryDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allValuableEntries: LiveData<List<Entry>> = entryDao.loadCharVotes("valuable")

    suspend fun insert(entry: Entry) {
        entryDao.insert(entry)
    }

    suspend fun deleteRoundEntries() {
        entryDao.deleteRoundEntries()
    }
}