package com.example.bitego

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

 class BocadilloAdapter(
        private val context: Context,
        private val bocadillos: Map<String, List<String>>
 ) : BaseAdapter() {

    private val dias = bocadillos.keys.toList()

    override fun getCount(): Int {
        return dias.size
    }

    override fun getItem(position: Int): Any {
        val dia = dias[position]
        return bocadillos[dia] ?: listOf<String>()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.linear_bocatas, parent, false)

        val dia = dias[position]
        val listaBocadillos = bocadillos[dia]


        val fechaTextView: TextView = view.findViewById(R.id.Fecha)
        fechaTextView.text = dia

        val bocadilloTextView: TextView = view.findViewById(R.id.Bocata)
        bocadilloTextView.text = listaBocadillos?.joinToString("\n") ?: "No disponible"

        return view
    }
}