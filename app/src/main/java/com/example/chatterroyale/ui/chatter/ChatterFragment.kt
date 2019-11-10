package com.example.chatterroyale.ui.chatter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chatterroyale.R

class ChatterFragment : Fragment() {

    private lateinit var chatterViewModel: ChatterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatterViewModel =
            ViewModelProviders.of(this).get(ChatterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chatter, container, false)
        val textView: TextView = root.findViewById(R.id.text_chatter)
        chatterViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}