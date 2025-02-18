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

        val navController = navHostFragment?.navController

        if (navController != null) {
            val navGraph = navController.navInflater.inflate(
                when(rol){
                    "alumno" -> R.navigation.nav_alumno_graph
                    "administrador" -> R.navigation.nav_admin_graph
                    else -> R.navigation.nav_cocina_graph
                }
            )
        }

        /*when(rol){
            "admin" -> {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()
            }
            "cocina" -> {
                val intent = Intent(this, CocinaActivity::class.java)
            }
            "alumno" -> {
                val intent = Intent(this, AlumnoActivity::class.java)
            }
        }*/
    }
}