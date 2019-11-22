package com.example.chatterroyale.ui.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chatterroyale.MainActivity
import com.example.chatterroyale.R
import kotlinx.android.synthetic.main.fragment_stats.*

class StatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel
    private lateinit var main: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = requireActivity() as MainActivity
        statsViewModel = ViewModelProviders.of(this).get(StatsViewModel::class.java)
        statsViewModel.findUserStats(main.MyUser.uid.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_stats, container, false)

        //Observer watches something and updates its view accordingly. Observer needs to be told to watch a value.
        val expObs = Observer<Double> { result -> totEXPVal.text = result?.toString() }
        statsViewModel.totEXP.observe(this,expObs)

        val unObs = Observer<Double> { result -> unspentEXPVal.text = result?.toString() }
        statsViewModel.unspentEXP.observe(this,unObs)

        var roundEXPVal = root.findViewById<TextView>(R.id.roundEXPVal)
        roundEXPVal.text = main.MyUser.roundEXP.toString()
        var unspentEXPVal = root.findViewById<TextView>(R.id.unspentRoundEXPVal)
        unspentEXPVal.text = main.MyUser.unspentRoundEXP.toString()

        val upRObs = Observer<Double> { result -> upvotesGivenVal.text = result?.toString() }
        statsViewModel.upvotesReceived.observe(this,upRObs)

        val upGObs = Observer<Double> { result -> upvotesReceivedVal.text = result?.toString() }
        statsViewModel.upvotesGiven.observe(this,upGObs)

        val doRObs = Observer<Double> { result -> downvotesReceivedVal.text = result?.toString() }
        statsViewModel.downvotesReceived.observe(this,doRObs)

        val doGObs = Observer<Double> { result -> downvotesGivenVal.text = result?.toString() }
        statsViewModel.downvotesGiven.observe(this,doGObs)

        val elimObs = Observer<Double> { result -> eliminationsVal.text = result?.toString() }
        statsViewModel.eliminations.observe(this,elimObs)

        val tagRObs = Observer<Double> { result -> tagsReceivedVal.text = result?.toString() }
        statsViewModel.tagsReceived.observe(this,tagRObs)

        val tagGObs = Observer<Double> { result -> tagsGivenVal.text = result?.toString() }
        statsViewModel.tagsGiven.observe(this,tagGObs)

        val TTRObs = Observer<String> { result -> topTagReceivedVal.text = result?.toString() }
        statsViewModel.topTagReceived.observe(this,TTRObs)

        val TTGObs = Observer<String> { result -> topTagGivenVal.text = result?.toString() }
        statsViewModel.topTagGiven.observe(this,TTGObs)

        val totEObs = Observer<Double> { result -> totalEntriesVal.text = result?.toString() }
        statsViewModel.totEntries.observe(this,totEObs)

        val winEObs = Observer<Double> { result -> winningEntriesVal.text = result?.toString() }
        statsViewModel.winningEntries.observe(this,winEObs)

        val finEObs = Observer<Double> { result -> finalistEntriesVal.text = result?.toString() }
        statsViewModel.finalistEntries.observe(this,finEObs)

        val elimEObs = Observer<Double> { result -> eliminatedEntriesVal.text = result?.toString() }
        statsViewModel.eliminatedEntries.observe(this,elimEObs)

        val avgRObs = Observer<Double> { result -> averageRankVal.text = result?.toString() }
        statsViewModel.averageRank.observe(this,avgRObs)

        val avgLObs = Observer<Double> { result -> averagePostLengthVal.text = result?.toString() }
        statsViewModel.averagePostLength.observe(this,avgLObs)

        return root
    }

    override fun onResume() {
        super.onResume()
        main.fabOn(false)
    }
}