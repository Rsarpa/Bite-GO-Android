package com.example.bitego

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bitego.databinding.ActivityMainBinding
import com.example.bitego.viewmodels.UsuarioViewModel

class MainActivity:AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtener el rol
        val rol = intent.getStringExtra("rol")

        //recoger el fragment container con navHostFragment para posteriormente inflar el menu de navegación
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_graph_container) as NavHostFragment
        navController = navHostFragment.navController

        val botonNav = binding.bottonNav

        if (navController != null) {
            when (rol) {
                 "alumno" -> {
                     navController.setGraph(R.navigation.nav_alumno_graph)
                     botonNav.menu.clear()
                     botonNav.inflateMenu(R.menu.alumno_navegacion_menu)
                     botonNav.setupWithNavController(navController)
                 }
                 "administrador" -> {
                     navController.setGraph(R.navigation.nav_admin_graph) //inflar el navGraph
                     botonNav.menu.clear() //limpiar el menu
                     botonNav.inflateMenu(R.menu.admin_navegation_menu)  //mostrar menu
                     botonNav.setupWithNavController(navController) //hacerlo funcional

                     //llamar al metodo cerrarSesion si pulsa en el item de Salir
                     botonNav.setOnItemSelectedListener { item ->
                         when (item.itemId){
                             R.id.salir -> {
                                 cerrarSesion()
                                 true
                             }else -> {
                                 navController.navigate(item.itemId)
                                 true
                             }
                         }
                     }
                 }
                 else -> navController.setGraph(R.navigation.nav_alumno_graph)
            }
        }
    }
    private fun cerrarSesion() {
        usuarioViewModel.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}