package com.example.chatterroyale.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatterroyale.R
import com.example.chatterroyale.adapters.ChatterRVAdapter
import com.example.chatterroyale.ui.chatter.ChatterFragment
import com.example.chatterroyale.ui.chatter.ChatterViewModel
import com.example.chatterroyale.ui.perks.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_chatter.*

/*PURPOSE: Allow users to see logistical information about about their account
           On this screen, users should be able to sign out of and delete their accounts*/

class ProfileFragment : Fragment() {

    private val TAG = ChatterFragment::class.simpleName

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var mAdapter: ChatterRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_profile,container,false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}