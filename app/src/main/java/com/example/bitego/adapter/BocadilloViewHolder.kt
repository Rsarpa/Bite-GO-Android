package com.example.bitego.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.BocadilloHoy
import com.example.bitego.R
import com.example.bitego.databinding.ItemPedirbocataBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.InputStreamReader
import java.time.LocalDate

class BocadilloViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val binding = ItemPedirbocataBinding.bind(view)

    fun render(bocadilloModel: DatosBocadillo){
        binding.tvNombreBocadillo.text = bocadilloModel.bocadillo
        binding.tvTipo.text = bocadilloModel.tipo
        binding.tvCoste.text = bocadilloModel.precio
        //binding.imageView.drawable = bocadilloModel.photo
    }
}