package com.example.bitego.adapter

import android.content.Context
import android.widget.TextView
import com.example.bitego.BocadilloHoy
import com.example.bitego.BocadilloSemana
import com.example.bitego.R
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.InputStreamReader
import java.time.LocalDate

class BocadilloProvider(private val context: Context) {

    fun dia(): String {
        val dia = LocalDate.now().plusDays(1)
        return dia.dayOfWeek.toString()
    }

    fun cargarBocadillosDesdeJson(): BocadilloHoy {
        val inputStream = context.resources.openRawResource(R.raw.bocadillosemana)
        val lector = InputStreamReader(inputStream)
        val tipo = object : TypeToken<BocadilloHoy>() {}.type
        return Gson().fromJson(lector, tipo)
    }

    fun obtenerBocadillo(): List<DatosBocadillo>{

        val bocadillomanana = cargarBocadillosDesdeJson()
        val diadeHoy = dia()

        val bocadillocaliente = bocadillomanana.bocadilloscalientes[diadeHoy] ?: "No hay bocadillo"
        val bocadillofrio = bocadillomanana.bocadillosfrios[diadeHoy] ?: "No hay bocadillo"

        return listOf(
            DatosBocadillo(
               bocadillo = bocadillocaliente,
                "Caliente",
                "4.50",
                ""
            ),
            DatosBocadillo(
                bocadillo = bocadillofrio,
                "Frío",
                "3.50",
                ""
            )
        )
    }


}