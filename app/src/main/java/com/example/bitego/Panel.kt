package com.example.bitego

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import java.io.InputStreamReader
import java.time.LocalDate

class Panel : AppCompatActivity() {

    lateinit var calendario: ImageView
    lateinit var menuUsuario: ImageView
    lateinit var mainPanel: ImageView
    lateinit var encargar: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_panel)


        calendario = findViewById(R.id.programacion)
        menuUsuario = findViewById(R.id.menu)
        mainPanel = findViewById(R.id.home)
        //encargar = findViewById(R.id.encargo)


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


        fun dia(): String {
            val dia = LocalDate.now().plusDays(1)
            return dia.dayOfWeek.toString()
        }

        fun cargarBocadillosDesdeJson(): BocadilloHoy {
            val inputStream = resources.openRawResource(R.raw.bocadillosemana)
            val lector = InputStreamReader(inputStream)
            val tipo = object : TypeToken<BocadilloHoy>() {}.type
            return Gson().fromJson(lector, tipo)
        }

        val bocadillomanana = cargarBocadillosDesdeJson()
        val diadeHoy = dia()

        val bocadillocaliente = bocadillomanana.bocadilloscalientes[diadeHoy]
        val bocadillofrio = bocadillomanana.bocadillosfrios[diadeHoy]

        val textocalientes = findViewById<TextView>(R.id.calientehoy)
        val textofrios = findViewById<TextView>(R.id.friohoy)

        textofrios.text = bocadillofrio ?: "No hay bocadillos"
        textocalientes.text = bocadillocaliente ?: "No hay bocadillos"
    }

    private fun programacion(){
        //llamar a la variable de inicio de sesion
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
        val intent = Intent(this, Panel::class.java)
        startActivity(intent)
    }
}
