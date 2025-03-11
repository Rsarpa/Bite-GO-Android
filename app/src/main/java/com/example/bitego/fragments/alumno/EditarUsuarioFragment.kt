package com.example.bitego.fragments.alumno

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bitego.R
import com.example.bitego.databinding.FragmentEditarUsuarioBinding
import com.example.bitego.viewmodels.UsuarioViewModel

class EditarUsuarioFragment : Fragment() {

    private var _binding: FragmentEditarUsuarioBinding? = null
    private val binding get() = _binding!!

    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioViewModel.usuarioAutenticado.observe(viewLifecycleOwner) { usuario ->
            if (usuario != null) {
                binding.etNombre.setText(usuario.nombre)
                binding.etApellidos.setText(usuario.apellidos)
                binding.etCurso.setText(usuario.curso)
                binding.etCorreo.setText(usuario.email)
                binding.etPassword.setText(usuario.psswd)
            }
        }

        //mostrar mensajes
        usuarioViewModel.mensaje.observe(viewLifecycleOwner) { mensaje ->
            if (!mensaje.isNullOrEmpty()) {
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            }
        }

        //mostrar error msg
        usuarioViewModel.errorMensaje.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGuardar.setOnClickListener {
            val nuevoNombre = binding.etNombre.text.toString().trim()
            val nuevosApellidos = binding.etApellidos.text.toString().trim()
            val nuevoCurso = binding.etCurso.text.toString().trim()
            val email = binding.etCorreo.text.toString().trim()
            val nuevaContrasenya = binding.etPassword.text.toString().trim()

            //metodo del vm para actualizar el usuario
            usuarioViewModel.updateUsuario(nombre = nuevoNombre, apellidos = nuevosApellidos, curso = nuevoCurso, email = email, password = nuevaContrasenya)
            //volver atrás
            findNavController().navigate(R.id.action_editarUsuarioFragment_to_perfilFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}