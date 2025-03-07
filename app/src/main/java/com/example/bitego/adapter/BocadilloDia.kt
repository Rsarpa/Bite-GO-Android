package com.example.bitego.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.R
import com.example.bitego.databinding.ItemBocadilloDeHoyBinding
import com.example.bitego.modelos.Bocadillo

class BocadilloDia(private var bocadillos: List<Bocadillo>, private val onItemClick: (Bocadillo) -> Unit) : RecyclerView.Adapter<BocadilloDia.BocadilloViewHolder>()  {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BocadilloViewHolder {
        val binding = ItemBocadilloDeHoyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BocadilloViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BocadilloViewHolder, position: Int) {
        val bocadillo = bocadillos[position]
        holder.bind(bocadillo, position)
    }

    override fun getItemCount(): Int = bocadillos.size

    fun actualizarLista(nuevaLista: List<Bocadillo>) {
        println("DEBUG: Adaptador recibe -> ${nuevaLista.size} bocadillos")
        bocadillos = nuevaLista
        notifyDataSetChanged()
    }


    inner class BocadilloViewHolder(private val binding: ItemBocadilloDeHoyBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bocadillo: Bocadillo, position: Int) {
            println("DEBUG: Mostrando bocadillo -> ${bocadillo.nombre}")
            bocadillo.alergenos
            binding.tvNombreBocadillo.text = bocadillo.nombre
            binding.tvAlergenos.text = "Alergenos: ${bocadillo.nombresAlergenos?.joinToString(", ") ?: "Ninguno"}"
            binding.tvCoste.text = "${bocadillo.coste}€"
            binding.tvDia.text = bocadillo.dia
            binding.tvTipo.text = bocadillo.tipo

            // Cargar el icono correctamente desde drawable
            binding.imageView.setImageResource(R.drawable.sandwich) // Icono por defecto si no encuentra el recurso

            // Resaltar el bocadillo seleccionado
            binding.root.setBackgroundResource(
                if (position == selectedPosition) R.color.white else android.R.color.white
            )

            // Manejo de clic para selección del bocadillo
            binding.root.setOnClickListener {
                val oldPosition = selectedPosition
                selectedPosition = adapterPosition
                if (oldPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(oldPosition)
                }
                notifyItemChanged(selectedPosition)

                // Enviar el bocadillo seleccionado al fragmento
                onItemClick(bocadillo)
            }
        }
    }
}