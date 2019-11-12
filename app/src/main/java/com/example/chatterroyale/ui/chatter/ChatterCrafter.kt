package com.example.chatterroyale.ui.chatter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.example.chatterroyale.listItems.ChatterData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.synthetic.main.fragment_crafter.*
import java.sql.Timestamp
import java.time.Instant
import java.util.*

class ChatterCrafter : Fragment(){

    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private lateinit var main: MainActivity
    private lateinit var chatterViewModel : ChatterViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        chatterViewModel = ViewModelProviders.of(this).get(ChatterViewModel::class.java)

        if (chatterViewModel?.chatterPost?.value != "") {
            //Log.d("Start",chatterViewModel.chatterPost.value)
            chatterCrafterText?.setText(chatterViewModel.chatterPost.value)
        }

        val postObserver = Observer <String> {
            post -> chatterCrafterText.setText(post.toString())
        }

        chatterViewModel.getPost().observe(this, postObserver)

        main = requireActivity() as MainActivity
        main.fabOn(false)

        val root = inflater.inflate(R.layout.fragment_crafter,container,false)

        return root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crafterSubmitButton.setOnClickListener {
            if (chatterCrafterText.text.toString() != "") {
                submitEntry(chatterCrafterText.text.toString())
            }
            else {
                Snackbar.make(view, "Text is required bruh", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null) {
            chatterCrafterText.setText(savedInstanceState?.getString("chatterPost"))
        }
        else if (main.MyEntry.entryPost != ""){
            chatterCrafterText.setText(main.MyEntry.entryPost)
        }
    }

    override fun onResume() {
        super.onResume()
        main.fabOn(false)
        if(main.MyEntry.sTime != null){
            lastChatTime.text = main.MyEntry.sTime.toString()
        }
    }

    //Save any text and show the fab
    override fun onPause() {
        super.onPause()
        chatterViewModel.chatterPost.value = chatterCrafterText.text.toString()
        main.MyEntry.entryPost = chatterCrafterText.text.toString()
        Log.d("End",chatterViewModel.chatterPost.value)

        main.fabOn(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("Save",chatterCrafterText.text.toString())
        outState.putString("chatterPost",chatterCrafterText.text.toString())
    }

    fun submitEntry(post:String){
        var sTime : Date = Calendar.getInstance().time
        main.MyEntry.sTime = sTime
        val entry = ChatterData(
            post,
            1,
            sTime
            )
        firestoreDB?.collection("entries")?.add(entry)
    }
}