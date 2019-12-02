package com.example.chatterroyale

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.Gravity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStoreOwner
import com.example.chatterroyale.listItems.ChatterEntry
import com.example.chatterroyale.room.Entry
import com.example.chatterroyale.room.EntryViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_chatter.*

class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()
    lateinit var mainViewModel: MainViewModel
    lateinit var entryViewModel: EntryViewModel
    private lateinit var drawerLayout: DrawerLayout
    lateinit var existingVotes : List<Entry>
    var MyUser : User = User()
    var MyEntry : ChatterEntry = ChatterEntry()
    var round : Int? = null
    var stage : Int? = null
    var lastStage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //LAYOUT AND MENU SETUP
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        var navController = findNavController(R.id.nav_host_fragment)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { _ ->
            navController.navigate(R.id.action_global_chatterCrafter)
        }

        navView.itemIconTintList = null

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_chatter, R.id.nav_store,
                R.id.nav_stats, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        entryViewModel = ViewModelProvider(this).get(EntryViewModel(application)::class.java)
        //*****************************************************************

        FirebaseApp.initializeApp(this)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.watchCurrentStage().observe(this, Observer { currentStage ->
            if(MyUser.stage != currentStage.toInt()){ //New stage
                MyUser.postsVotedOn.clear() //Clear array of posts that were voted on whenever the stage is updated
                MyUser.roundEXP = 0
                MyUser.unspentRoundEXP = 0
                MyUser.stageVotes=40  //Reset vote limit
            }
            MyUser.stage = currentStage.toInt()
            stage = currentStage.toInt()
        })

        mainViewModel.watchCurrentRound().observe(this, Observer { currentRound ->
            if(MyUser.round != currentRound.toInt()){ // New Round
                //If this is a new round we don't recognize, delete the cached entry data in SQLite
                //entryViewModel.deleteRoundEntries()
                entryViewModel.deleteRoundEntries()
                mainViewModel.existingVotes.clear()
            }
            MyUser.round = currentRound.toInt()
            round = currentRound.toInt()
        })

        mainViewModel.watchCurrentUser().observe(this, Observer { currentUser ->
            MyUser.uid = currentUser.uid
        })

        mainViewModel.getOldVotes(MyUser.uid.toString(),"Valuable").observe(this, Observer { votes ->
            MyUser.valuableVotes = votes
        })

        mainViewModel.getOldVotes(MyUser.uid.toString(),"Intelligent").observe(this, Observer { votes ->
            MyUser.intelligentVotes = votes
        })

        mainViewModel.getOldVotes(MyUser.uid.toString(),"Funny").observe(this, Observer { votes ->
            MyUser.funnyVotes = votes
        })

        mainViewModel.getOldVotes(MyUser.uid.toString(),"Original").observe(this, Observer { votes ->
            MyUser.originalVotes = votes
        })

        mainViewModel.getEXP(MyUser.uid.toString(),"total").observe(this, Observer { EXP ->
            MyUser.EXP = EXP
        })
        mainViewModel.getEXP(MyUser.uid.toString(),"unspent").observe(this, Observer { EXP ->
            MyUser.unspentEXP = EXP
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

/*    override fun onDown(event: MotionEvent): Boolean {
        Log.d("test","onDown")
        return true
    }

    override fun onFling(event1: MotionEvent, event2: MotionEvent,
                         velocityX: Float, velocityY: Float): Boolean {
        Log.d("test","onDown")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d("test","onDown")
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent,
                          distanceX: Float, distanceY: Float): Boolean {
        Log.d("test","onDown")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d("test","onDown")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d("test","onDown")
        return true
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

    public fun fabOn(on:Boolean){
        if(on == true){
            fab.show()
        }
        if(on == false){
            fab.hide()
        }
    }

    fun incrementCxArray(){
        if(MyUser.i < MyUser.cxLength-1){
            MyUser.i+=1
        }
        else if(MyUser.i >= MyUser.cxLength-1){
            MyUser.i=0
        }
    }

    inner class recordVote : AsyncTask<Entry,Void,Void>(){
        override fun doInBackground(vararg params: Entry?): Void? {
            val entry = params.get(0)
            if (entry != null) {
                entryViewModel.insert(entry)
            }
            return null
        }
    }

}
