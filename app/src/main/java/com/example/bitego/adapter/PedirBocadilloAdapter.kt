package com.example.bitego.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.R

class PedirBocadilloAdapter(private val bocadilloList: List<DatosBocadillo>):RecyclerView.Adapter<BocadilloViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BocadilloViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BocadilloViewHolder(layoutInflater.inflate(R.layout.item_pedirbocata, parent, false))
    }

    override fun getItemCount(): Int = bocadilloList.size

    override fun onBindViewHolder(holder: BocadilloViewHolder, position: Int) {
        val item = bocadilloList[position]
        holder.render(item)
    }
}