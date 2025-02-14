package com.example.bitego

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.bitego.fragments.alumno.AlumnoFragment

private lateinit var listView: ListView
lateinit var calendario: ImageView
lateinit var menuUsuario: ImageView
lateinit var panelPrincipal: ImageView
lateinit var logoutButton: Button

class MenuUser : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_user)

        // Inicializamos el ListView
        listView = findViewById(R.id.listView)
        calendario = findViewById(R.id.programacion)
        menuUsuario = findViewById(R.id.menu)
        panelPrincipal = findViewById(R.id.home)
        logoutButton = findViewById(R.id.logout)

        //llamar al metodo logout cuando pulse
        calendario.setOnClickListener{
            programacion()
        }

        menuUsuario.setOnClickListener{
            menu()
        }

        panelPrincipal.setOnClickListener{
            principal()
        }

        logoutButton.setOnClickListener{
            logout()
        }

        val datos = listOf(
            "Usuario: Admin",
            "Correo Electronico: admin@gmail.com",
            "Contraseña: ********"
        )

        val listViewUsuario = findViewById<ListView>(R.id.listView)

        // Crear un adaptador con los datos de la lista
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, datos)

        // Establecemos el adaptador al ListView
        listViewUsuario.adapter = adapter

    }

    private fun logout(){
        //llamar a la variable de inicio de sesion
        val intent = Intent(this, MainActivity::class.java)

        //limpiar la actividad del usuario
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun menu(){
        val intent = Intent(this, MenuUser::class.java)

        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        //finish()
    }

    private fun programacion(){
        //llamar a la variable de inicio de sesion
        val intent = Intent(this, Historico::class.java)

        //limpiar la actividad del usuario
        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        //finish()
    }

    private fun principal(){
        val intent = Intent(this, AlumnoFragment::class.java)

        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}