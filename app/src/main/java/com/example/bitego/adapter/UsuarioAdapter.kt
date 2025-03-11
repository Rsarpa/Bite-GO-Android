package com.example.bitego.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.databinding.ItemCrudAlumnoBinding
import com.example.bitego.modelos.Usuario

class UsuarioAdapter(
    private var listaUsuarios: List<Usuario>,
    private val onEditarClick: (Usuario) -> Unit,
    private val onEliminarClick: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.AlumnoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val binding = ItemCrudAlumnoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("DEBUG", "Adaptador -> onCreateViewHolder() llamado")
        return AlumnoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val usuario = listaUsuarios[position]
        holder.bind(usuario)
    }

    override fun getItemCount(): Int {
        Log.d("DEBUG", "Adaptador Usuarios -> getItemCount() devuelve ${listaUsuarios.size}")
        return listaUsuarios.size
    }

    inner class AlumnoViewHolder(private val binding: ItemCrudAlumnoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(usuario: Usuario) {
            binding.tvNombreAlumno.text = usuario.nombre
            binding.tvApellidos.text = usuario.apellidos
            binding.tvId.text = "# ${usuario.uId}"
            binding.tvEmail.text = usuario.email

            binding.btnEditar.setOnClickListener { onEditarClick(usuario) }
            binding.btnEliminar.setOnClickListener { onEliminarClick(usuario) }
        }
    }

    fun actualizarLista(nuevaLista: List<Usuario>) {
        Log.d("DEBUG", "Adaptador -> actualizando lista con ${nuevaLista.size} usuarios")
        listaUsuarios = nuevaLista
        notifyDataSetChanged()
    }
}
