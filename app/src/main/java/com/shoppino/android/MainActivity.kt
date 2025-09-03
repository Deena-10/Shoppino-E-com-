package com.shoppino.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shoppino.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🎨 **SETUP SHOPPINO TOOLBAR**
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Shoppino"
            setDisplayHomeAsUpEnabled(false)
        }

        // 🧭 **SETUP NAVIGATION**
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 🔽 **SETUP BOTTOM NAVIGATION**
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)

        // 🎯 **NAVIGATION LISTENER FOR DYNAMIC TITLES**
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    supportActionBar?.title = "Shoppino"
                }
                R.id.catalogFragment -> {
                    supportActionBar?.title = "Products"
                }
                R.id.cartFragment -> {
                    supportActionBar?.title = "Shopping Cart"
                }
                R.id.profileFragment -> {
                    supportActionBar?.title = "Profile"
                }
            }
        }
    }
}
