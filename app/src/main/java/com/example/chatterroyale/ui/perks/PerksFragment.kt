package com.example.chatterroyale.ui.perks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chatterroyale.R

class PerksFragment : Fragment() {

    private lateinit var perksViewModel: PerksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        perksViewModel =
            ViewModelProviders.of(this).get(PerksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_perks, container, false)
        val textView: TextView = root.findViewById(R.id.text_perks)
        perksViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}