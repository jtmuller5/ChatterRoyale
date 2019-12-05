package com.example.chatterroyale.ui.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatterroyale.listItems.CategoryPrompts
import com.example.chatterroyale.listItems.ChatterEntry
import com.google.firebase.firestore.FirebaseFirestore

class RepoViewModel : ViewModel() {

    private val TAG = RepoViewModel::class.simpleName
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()

    var promptEntriesList: MutableLiveData<List<ChatterEntry>> = MutableLiveData()
    var categoryPromptsList: MutableLiveData<List<String>> = MutableLiveData()

    //List the submissions for a single prompt
    fun findPromptEntries(prompt:String) : LiveData<List<ChatterEntry>> {
        val list = mutableListOf<ChatterEntry>()
        firestoreDB?.collection("entries")?.whereEqualTo("prompt",prompt)
            ?.get()?.addOnSuccessListener { entries ->
                for (entry in entries) {
                    list.add(entry.toObject(ChatterEntry::class.java))
                }
                promptEntriesList.postValue(list)
            }
            ?.addOnFailureListener { e ->
                Log.e("Failed", e.toString())
            }
        return promptEntriesList
    }

    //List the prompts for a single category
    fun findcategoryPrompts(cat:String) : LiveData<List<String>> {
        val list = mutableListOf<String>()
        firestoreDB?.collection("prompts")?.whereEqualTo("category",cat)
            ?.get()?.addOnSuccessListener { entries ->
                for (entry in entries) {
                    var catPrompt = entry.toObject(CategoryPrompts::class.java)
                    list.add(catPrompt.prompt)
                }
                categoryPromptsList.postValue(list)
            }
            ?.addOnFailureListener { e ->
                Log.e("Failed", e.toString())
            }
        return categoryPromptsList
    }
}