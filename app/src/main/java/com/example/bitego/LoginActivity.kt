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

            //comprobar que los campos no estén vacios
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, rellene los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            usuarioViewModel.verificarUsuario(username, password)
        }

        //metodo de autenticación desde el ViewModel
        usuarioViewModel.usuarioAutenticado.observe(this){usuario ->
            if (usuario != null ){
                val rol = usuario.rol?.lowercase() ?: "alumno" //definir rol

                //llamar al MainActivity pasandole el rol
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("rol", rol)
                startActivity(intent)
                finish()
            }
        }

        //lanzar mensajes de error del ViewModel
        usuarioViewModel.errorMensaje.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}