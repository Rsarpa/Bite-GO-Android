package com.example.bitego.fragments.alumno

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.bitego.BocadilloHoy
import com.example.bitego.Historico
import com.example.bitego.MenuUser
import com.example.bitego.R
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import java.io.InputStreamReader
import java.time.LocalDate

class AlumnoActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_panel)

        val actionBar = supportActionBar
        actionBar?.title = "Bite&GO"

    }

    private fun programacion(){
        //llamar a la variable de inicio de sesion
        //todo cambiar a clase fragment
        val intent = Intent(this, Historico::class.java)
        startActivity(intent)
        //finish()
    }

    private fun menu(){
        val intent = Intent(this, MenuUser::class.java)
        startActivity(intent)
        //finish()
    }

    private fun principal(){
        val intent = Intent(this, AlumnoFragment::class.java)
        startActivity(intent)
    }
}
