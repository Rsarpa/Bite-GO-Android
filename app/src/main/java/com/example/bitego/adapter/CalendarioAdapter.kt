package com.example.bitego.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.databinding.ItemBocadilloBinding
import com.example.bitego.modelos.Bocadillo
import com.example.bitego.R

class CalendarioAdapter(
    private var bocadillos: List<Bocadillo>
) : RecyclerView.Adapter<CalendarioAdapter.BocadilloViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BocadilloViewHolder {
        val binding = ItemBocadilloBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("DEBUG", "Adaptador -> onCreateViewHolder() llamado")
        return BocadilloViewHolder(binding)
    }

    fun actualizarLista(nuevaLista: List<Bocadillo>) {
        Log.d("DEBUG", "Adaptador -> actualizando lista con ${nuevaLista.size} bocadillos")
        bocadillos = nuevaLista
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BocadilloViewHolder, position: Int) {
        val bocadillo = bocadillos[position]

        Log.d("CalendarioAdapter", "Bocadillo en posición $position: $bocadillo")

        with(holder.binding) {
            txtNombreAlumno.text = bocadillo.nombre
            txtDescripcion.text = bocadillo.descripcion
            txtAlergenos.text = "Alergenos: ${bocadillo.nombresAlergenos?.joinToString(", ") ?: "Ninguno"}"
            txtCoste.text = "${bocadillo.coste}€"
            txtDia.text = "${bocadillo.dia}"
            txtTipo.text = "Tipo: ${bocadillo.tipo}"
            imgBocadillo.setImageResource(R.drawable.sandwich)
        }

        Log.d("DEBUG", "Adaptador -> Bocadillo en posición $position -> ${bocadillo.nombre}")
    }

    override fun getItemCount(): Int {
        Log.d("DEBUG", "Adaptador -> getItemCount() devuelve ${bocadillos.size}")
        return bocadillos.size
    }

    class BocadilloViewHolder(val binding: ItemBocadilloBinding) : RecyclerView.ViewHolder(binding.root)
}
