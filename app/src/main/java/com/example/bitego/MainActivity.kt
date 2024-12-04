package com.example.bitego

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var usernameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var loginButton : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.username)
        passwordInput = findViewById(R.id.psswd)
        loginButton = findViewById(R.id.button)

        usernameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })


        loginButton.setOnClickListener{

            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isEmpty())
                Toast.makeText(this, "Campo vacío", Toast.LENGTH_LONG).show()

            else if (password.isEmpty())
                Toast.makeText(this, "Introduce una contraseña", Toast.LENGTH_LONG).show()

            else if (username == "admin" && password == "asdf1234") {
                val intento = Intent(this, Panel::class.java)
                intento.putExtra("admin", username)
                startActivity(intento)
                finish()

            }else{
                Toast.makeText(this, "Usuario y Contraseña incorrectos", Toast.LENGTH_LONG).show()
            }

            Log.i("Test Credentials", "Username: $username and Password: $password")
        }
    }
}