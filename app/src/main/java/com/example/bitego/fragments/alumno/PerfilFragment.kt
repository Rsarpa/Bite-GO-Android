package com.example.bitego.fragments.alumno

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bitego.LoginActivity
import com.example.bitego.R
import com.example.bitego.databinding.FragmentPerfilBinding
import com.example.bitego.viewmodels.UsuarioViewModel

class PerfilFragment : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)

        // Observar el usuario autenticado
        usuarioViewModel.usuarioAutenticado.observe(viewLifecycleOwner) { usuario ->
            usuario?.let {
                val usuarioInfo = listOf(
                    "Nombre: ${it.nombre}",
                    "Apellidos: ${it.apellidos}",
                    "Email: ${it.email}",
                    "Curso: ${it.curso}",
                    "Rol: ${it.rol}"
                )

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, usuarioInfo)
                binding.listView.adapter = adapter
            }
        }

        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.action_perfilFragment_to_editarUsuarioFragment)
        }

        // Botón de Cerrar Sesión
        binding.logout.setOnClickListener {
            usuarioViewModel.signOut() // Cierra sesión en Firebase

            // Redirigir al usuario al LoginActivity
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            // Limpia la pila de actividades con FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK, evitando que el usuario vuelva atrás con el botón "Atrás".
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Limpia la pila de actividades
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

