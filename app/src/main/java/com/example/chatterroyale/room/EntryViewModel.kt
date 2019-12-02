package com.example.chatterroyale.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class EntryViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: EntryRepository
    // LiveData gives us updated words when they change.
    val allValuableEntries: LiveData<List<Entry>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val entryDao = EntryRoomDatabase.getDatabase(application,viewModelScope).entryDao()
        repository = EntryRepository(entryDao)
        allValuableEntries = repository.allValuableEntries
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(entry: Entry) = viewModelScope.launch {
        repository.insert(entry)
    }

    fun deleteRoundEntries() = viewModelScope.launch {
        repository.deleteRoundEntries()
    }
}