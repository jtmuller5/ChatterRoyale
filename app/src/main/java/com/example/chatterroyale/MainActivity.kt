package com.example.chatterroyale

import android.os.Bundle
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chatterroyale.listItems.ChatterEntry
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var firestoreDB: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private lateinit var mainViewModel: MainViewModel
    var MyUser : User = User()
    var MyEntry : ChatterEntry = ChatterEntry()
    var round : Int? = null
    var stage : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //LAYOUT AND MENU SETUP
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
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
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //*****************************************************************

        FirebaseApp.initializeApp(this)
    }

    override fun onStart() {
        super.onStart()

        mainViewModel.watchCurrentStage().observe(this, Observer { currentStage ->
            MyUser.stage = currentStage.toInt()
            stage = currentStage.toInt()
        })

        mainViewModel.watchCurrentRound().observe(this, Observer { currentRound ->
            MyUser.round = currentRound.toInt()
            round = currentRound.toInt()
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

    public fun fabOn(on:Boolean){
        if(on == true){
            fab.show()
        }
        if(on == false){
            fab.hide()
        }
    }

}
