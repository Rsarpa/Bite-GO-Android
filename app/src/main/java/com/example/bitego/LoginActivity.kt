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
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bitego.databinding.ActivityLoginBinding

import com.example.bitego.fragments.alumno.DashboardAlumno

class LoginActivity:AppCompatActivity() {

    lateinit var usernameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var loginButton : Button

    @SuppressLint("MissingInflatedId")
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //usernameInput = findViewById(R.id.username)
        //passwordInput = findViewById(R.id.psswd)

        val opciones = arrayOf("Alumno", "Cocina", "Administrador")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        var selector = ""

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

        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })


        binding.button.setOnClickListener{

            val username = binding.username.text.toString()
            val password = binding.psswd.text.toString()

            if (username.isEmpty())
                Toast.makeText(this, "Campo vacío", Toast.LENGTH_LONG).show()

            else if (password.isEmpty())
                Toast.makeText(this, "Introduce una contraseña", Toast.LENGTH_LONG).show()

            else if (username == "admin" && password == "asdf1234") {

               //val intento = Intent(this, DashboardAlumno::class.java)
                val intento = Intent(baseContext, MainActivity::class.java)
                //intento.putExtra("login", username)


                if(selector.equals("Administrador")){
                    startActivity(intento.putExtra("rol","admin"))
                    finish()
                }else if (selector.equals("Cocina")) {
                    intento.putExtra("rol", "cocina")
                }else if(selector.equals("Alumno")){
                    intento.putExtra("rol", "alumno")
                }else{
                    Toast.makeText(this, "Escoge un rol", Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(this, "Usuario y Contraseña incorrectos", Toast.LENGTH_LONG).show()
            }

            Log.i("Test Credentials", "Username: $username and Password: $password")
        }
    }
}