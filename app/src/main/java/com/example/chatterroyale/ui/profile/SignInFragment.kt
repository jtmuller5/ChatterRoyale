package com.example.chatterroyale.ui.repo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signin.*

class SignInFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var main: MainActivity
    val RC_SIGN_IN = 1

    // Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.PhoneBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())

    //Ensure that users cannot use the app without being signed in
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = requireActivity() as MainActivity
        main.fabOn(false)
        //main.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        main.supportActionBar?.hide()
        checkUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_signin, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_sign_in.setOnClickListener {
            // Create and launch sign-in intent
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                main.MyUser.user = user
                Log.d("Login",user.toString())
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
            checkUser()
        }
    }

    fun checkUser(){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            main.MyUser.uid = user.uid
            main.supportActionBar?.show()
            main.findNavController(R.id.nav_host_fragment).navigate(R.id.action_signInFragment_to_nav_home)
        } else {
            // No user is signed in
            main.MyUser.uid = null
        }
    }
}