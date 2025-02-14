package com.example.bitego

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bitego.databinding.ActivityMainBinding
import com.example.bitego.fragments.admin.DashboardAdmin
import com.example.bitego.fragments.alumno.AlumnoActivity
import com.example.bitego.fragments.alumno.AlumnoFragment
import com.example.bitego.fragments.cocina.DashboardCocina
import com.example.bitego.viewmodels.UsuarioViewModel


class MainActivity:AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtener el valor del intent
        val rol = intent.getStringExtra("rol")

        when(rol){
            "admin" -> {
                val intent = Intent(this, DashboardAdmin::class.java)
                startActivity(intent)
                finish()
            }
            "cocina" -> {
                val intent = Intent(this, DashboardCocina::class.java)
            }
            "alumno" -> {
                val intent = Intent(this, AlumnoActivity::class.java)
            }
        }
    }
}