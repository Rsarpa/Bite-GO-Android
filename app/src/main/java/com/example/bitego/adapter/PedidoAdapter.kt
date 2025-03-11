package com.example.bitego.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.R
import com.example.bitego.modelos.Pedido
import java.text.SimpleDateFormat
import java.util.Locale

class PedidoAdapter(private var pedidos: List<Pedido>):RecyclerView.Adapter<PedidoAdapter.BocadilloViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BocadilloViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BocadilloViewHolder(layoutInflater.inflate(R.layout.item_pedido, parent, false))
    }

    fun actualizarLista(nuevaLista: List<Pedido>) {
        pedidos = nuevaLista
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = pedidos.size

    override fun onBindViewHolder(holder: BocadilloViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.nombreBocadillo.text = pedido.bocadillo.nombre
        val fechayhora = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
        holder.fecha.text = fechayhora.format(pedido.fecha_hora.toDate())

        holder.retirado.text = if (pedido.estado) "Retirado" else "No retirado"
        holder.retirado.setBackgroundColor(
            if (pedido.estado) holder.itemView.context.getColor(R.color.verde)
            else holder.itemView.context.getColor(R.color.rojo)
        )

        holder.tipo.text = pedido.bocadillo.tipo

        holder.coste.text = "%.2f€".format(pedido.precio)

        // Cargar icono de bocadillo
        holder.img.setImageResource(R.drawable.sandwich)
    }

    class BocadilloViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nombreBocadillo: TextView = view.findViewById(R.id.nombreBocadillo)
        val fecha: TextView = view.findViewById(R.id.fecha)
        val tipo: TextView = view.findViewById(R.id.tipo)
        val retirado: TextView = view.findViewById(R.id.retirado)
        val coste: TextView = view.findViewById(R.id.coste)
        val img: ImageView = view.findViewById(R.id.imageView)
    }
}