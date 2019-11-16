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
import com.example.chatterroyale.UserData
import com.example.chatterroyale.listItems.ChatterData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_crafter.*
import java.util.*

class ChatterCrafter : Fragment(){

    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private lateinit var main: MainActivity
    private lateinit var chatterViewModel : ChatterViewModel

    val entriesRef = firestoreDB?.collection("entries")
    val usersRef = firestoreDB?.collection("users")

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
        //chatterViewModel.watchCurrentStage()

        main = requireActivity() as MainActivity
        main.fabOn(false)

        val root = inflater.inflate(R.layout.fragment_crafter,container,false)

        return root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crafterSubmitButton.setOnClickListener {
            var post = chatterCrafterText.text.toString()
            var message = validateEntry(post)
            if (message == "okay") {
                submitEntry(chatterCrafterText.text.toString())
                chatterCrafterText.text.clear()
                setLastChatTime()
            }
            else if(message == "no text"){
                Snackbar.make(view, "Text is required bruh", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
            else if(message == "too long"){
                Snackbar.make(view, "Text needs to be shorter bruh", Snackbar.LENGTH_LONG).setAction("Action", null).show()
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
        setLastChatTime()
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
            main.stage,
            sTime,
            main.MyUser.uid
            )

        entriesRef?.add(entry)

        val userDoc = usersRef?.document(main.MyUser.uid.toString())
        userDoc?.get()
            ?.addOnSuccessListener { user ->
                if(user.exists()){
                    Log.d("user",user.toString())
                    userDoc.update("totalEntries",FieldValue.increment(1))
                }
                else{
                    val newUser = UserData(
                    main.MyUser.uid.toString(),
                    1
                )
                    userDoc.set(newUser)

                }
            }

    }

    fun validateEntry(post:String):String{
     var message:String = "okay"
        if(post == ""){
            message = "no text"
        }
        else if(post.length > 100){
            message = "too long"
        }
        return message
    }

    fun setLastChatTime(){
        if(main.MyEntry.sTime != null){
            lastChatTime.text = main.MyEntry.sTime.toString()
        }
    }
}