package com.example.bitego

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.bitego.databinding.ActivityLoginBinding
import com.example.bitego.viewmodels.UsuarioViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity() {

    private lateinit var usuarioViewModel: UsuarioViewModel

    @SuppressLint("MissingInflatedId")
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseAuth.getInstance().signOut()

        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)


        binding.button.setOnClickListener{
            val username = binding.username.text.toString()
            val password = binding.psswd.text.toString()
            usuarioViewModel.verificarUsuario(username, password)
        }

        val opciones = arrayOf("Alumno", "Cocina", "Administrador")

        //establecer adapter del spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        var selector = ""

        //evento seleccion Rol
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                when(opciones[position]){
                    "Alumno" -> {
                        selector = "Alumno"
                    }
                    "Cocina" -> {
                        selector = "Cocina"
                    }
                    "Administrador" -> {
                        selector = "Administrador"
                    }else -> {
                    selector = ""
                }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        usuarioViewModel.isLogged.observe(this){logueo ->
            if (logueo){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("rol", selector)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}