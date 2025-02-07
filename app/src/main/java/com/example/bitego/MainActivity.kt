package com.example.bitego

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.bitego.databinding.ActivityLoginBinding
import com.example.bitego.databinding.ActivityMainBinding
import com.example.bitego.fragments.admin.DashboardAdmin
import com.example.bitego.fragments.alumno.DashboardAlumno
import com.example.bitego.fragments.cocina.DashboardCocina
import com.example.bitego.viewmodels.UsuarioViewModel


class MainActivity:AppCompatActivity() {

    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //cargar usuarios en la lista
        usuarioViewModel.fetchUsuarios()

        usuarioViewModel.listaUsuario.observe(this, Observer {
            listaUsuario -> if(listaUsuario != null){
                listaUsuario.let{
                    for ((clave ,usuario) in it){
                        if (usuario.nombre=="Ejemplo"){
                            //Toast.makeText("hola")
                        }
                    }
                }
        }
        })

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
                val intent = Intent(this, DashboardAlumno::class.java)
            }
        }
    }
}