package com.example.bitego.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.R
import com.example.bitego.databinding.ItemCrudBocadilloBinding
import com.example.bitego.modelos.Bocadillo

class BocadilloAdapter(
    private var listaBocadillos: List<Bocadillo>,
    private val onEditarClick: (Bocadillo) -> Unit,
    private val onEliminarClick: (Bocadillo) -> Unit
) : RecyclerView.Adapter<BocadilloAdapter.BocadilloViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BocadilloViewHolder {
        val binding = ItemCrudBocadilloBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("DEBUG", "Adaptador -> onCreateViewHolder() llamado")
        return BocadilloViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("DEBUG", "Adaptador Usuarios-> getItemCount() devuelve ${listaBocadillos.size}")
        return listaBocadillos.size
    }

    override fun onBindViewHolder(holder: BocadilloViewHolder, position: Int) {
        val bocadillo = listaBocadillos[position]
        holder.bind(bocadillo)
    }

    fun actualizarLista(nuevaLista: List<Bocadillo>) {
        Log.d("DEBUG", "Adaptador -> actualizando lista con ${nuevaLista.size} bocadillos")
        listaBocadillos = nuevaLista
        notifyDataSetChanged()
    }

    inner class BocadilloViewHolder(private val binding: ItemCrudBocadilloBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bocadillo: Bocadillo) {
            binding.txtNombreBocadillo.text = bocadillo.nombre
            binding.txtDia.text = bocadillo.dia
            binding.txtAlergenos.text = "Alergenos: ${bocadillo.nombresAlergenos?.joinToString(", ") ?: "Ninguno"}"
            binding.txtCoste.text = "Coste: ${bocadillo.coste}€"
            binding.txtTipo.text = "Tipo: ${bocadillo.tipo}"
            binding.txtId.text = "#${bocadillo.id}"
            binding.imgBocadillo.setImageResource(R.drawable.sandwich)


            binding.btnEditar.setOnClickListener { onEditarClick(bocadillo) }
            binding.btnEliminar.setOnClickListener { onEliminarClick(bocadillo) }
        }
    }
}
