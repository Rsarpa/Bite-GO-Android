package com.example.bitego

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.bitego.fragments.alumno.DashboardAlumno
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.InputStreamReader

class Historico : AppCompatActivity() {

    lateinit var calendario: ImageView
    lateinit var menuUsuario: ImageView
    lateinit var mainPanel: ImageView

    //TODO Binding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendario)


        calendario = findViewById(R.id.programacion)
        menuUsuario = findViewById(R.id.menu)
        mainPanel = findViewById(R.id.home)

        //llamar al metodo logout cuando pulse
        calendario.setOnClickListener{
            programacion()
        }

        menuUsuario.setOnClickListener{
            menu()
        }

        mainPanel.setOnClickListener{
            principal()
        }

        fun cargarBocadillosSemanaDesdeJson(): BocadilloSemana {
            val inputStream = resources.openRawResource(R.raw.bocadillosiguiente)
            val lector = InputStreamReader(inputStream)
            val tipo = object : TypeToken<BocadilloSemana>() {}.type
            return Gson().fromJson(lector, tipo)
        }

        val bocadilloSemana = cargarBocadillosSemanaDesdeJson()

        val bocadillosMap = bocadilloSemana.bocadillos
        bocadillosMap

        val gridView: GridView = findViewById(R.id.bocatasemana)
        val adapter = BocadilloAdapter(this, bocadillosMap)
        gridView.adapter = adapter

        val historialMovimientos = listOf(
            "18/10/2024: Bocadillo de pollo a la plancha",
            "21/11/2024: Bocadillo de con bacon Queso",
            "3/12/2024: Bocadillo de jamón"
        )

        val listViewHistorial = findViewById<ListView>(R.id.historial)

        val adapterhistorial = ArrayAdapter(this, android.R.layout.simple_list_item_1, historialMovimientos)

        listViewHistorial.adapter = adapterhistorial
    }

    private fun programacion(){
        //llamar a la variable de inicio de sesion
        val intent = Intent(this, Historico::class.java)

        //limpiar la actividad del usuario
        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        //finish()
    }

    private fun menu(){
        val intent = Intent(this, MenuUser::class.java)

        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        //finish()
    }

    private fun principal(){
        val intent = Intent(this, DashboardAlumno::class.java)
        startActivity(intent)
    }
}

