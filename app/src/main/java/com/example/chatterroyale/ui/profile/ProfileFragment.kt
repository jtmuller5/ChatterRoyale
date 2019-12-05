package com.example.chatterroyale.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.ChatterRVAdapter
import com.example.chatterroyale.ui.chatter.ChatterFragment
import com.example.chatterroyale.ui.repo.ProfileViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*

/*PURPOSE: Allow users to see logistical information about about their account
           On this screen, users should be able to sign out of and delete their accounts*/

class ProfileFragment : Fragment() {

    private val TAG = ChatterFragment::class.simpleName
    private lateinit var main : MainActivity

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var mAdapter: ChatterRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        main = requireActivity() as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_profile,container,false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileSignOut.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener {
                    main.fabOn(false)
                    main.findNavController(R.id.nav_host_fragment).navigate(R.id.action_profileFragment_to_signInFragment2)
                }
        }

        profileDelete.setOnClickListener {
            Snackbar.make(it, "You can't do that muahaha", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            /*AuthUI.getInstance()
                .delete(requireContext())
                .addOnCompleteListener {
                    // ...
                }*/
        }
    }
}