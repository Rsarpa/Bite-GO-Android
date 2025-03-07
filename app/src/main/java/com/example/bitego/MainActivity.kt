package com.example.bitego

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bitego.databinding.ActivityMainBinding

class MainActivity:AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtener el valor del intent
        val rol = intent.getStringExtra("rol")
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_graph_container) as NavHostFragment

        val navController = navHostFragment.navController

        val botonNav = binding.bottonNav
        botonNav.setupWithNavController(navController)
        //TODO controlar botonNav para admin
        var inflater = navController?.navInflater;
        if (navController != null) {
            when (rol) {
                 "alumno" -> inflater!!.inflate(R.navigation.nav_alumno_graph)
                 "administrador" -> inflater!!.inflate(R.navigation.nav_admin_graph)
                 else -> inflater!!.inflate(R.navigation.nav_admin_graph)
            }
        }
    }
}