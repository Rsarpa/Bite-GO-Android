package com.example.bitego.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitego.R
import com.example.bitego.adapter.UsuarioAdapter
import com.example.bitego.databinding.FragmentAdminAlumnoBinding
import com.example.bitego.modelos.Usuario
import com.example.bitego.viewmodels.UsuarioViewModel

class AdminAlumnoFragment : Fragment() {

    private var _binding: FragmentAdminAlumnoBinding? = null
    private val binding get() = _binding!!

    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private lateinit var usuarioAdapter: UsuarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAlumnoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioViewModel.fetchUsuarios()

        //  Usar binding.recyclerViewAlumnos en lugar de findViewById
        binding.rvAlumnos.layoutManager = LinearLayoutManager(requireContext())
        usuarioAdapter = UsuarioAdapter(
            listaUsuarios = emptyList(),
            onEditarClick = { usuario -> irAEditarUsuario(usuario) }, //  Editar usuario
            onEliminarClick = { usuario -> eliminarUsuario(usuario) }  // Eliminar usuario
        )
        binding.rvAlumnos.adapter = usuarioAdapter

        usuarioViewModel.usuarios.observe(viewLifecycleOwner) { usuarios ->
            if (usuarios.isNotEmpty()) {
                usuarioAdapter.actualizarLista(usuarios)
            } else {
                Toast.makeText(requireContext(), "No hay datos", Toast.LENGTH_SHORT).show()
            }
        }

        usuarioViewModel.errorMensaje.observe(viewLifecycleOwner) { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
        }

        binding.btnAnyadir.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardAdminAlumno_to_anyadirUsuarioFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evitar fugas de memoria
    }

    private fun irAEditarUsuario(usuario: Usuario) {
       // val action = AdminAlumnoFragmentDirections.actionDashboardAdminAlumnoToAdminEditarUsuarioFragment(usuario)
        //findNavController().navigate(action)
    }


    private fun eliminarUsuario(usuario: Usuario) {

        usuarioViewModel.deleteUsuario(usuario.uId!!) { exito ->
            if (exito) {
                Toast.makeText(requireContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show()
                usuarioViewModel.fetchUsuarios()
            } else {
                Toast.makeText(requireContext(), "Error al eliminar usuario", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

}