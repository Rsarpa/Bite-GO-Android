package com.example.bitego.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bitego.R
import com.example.bitego.databinding.FragmentAdminEditarUsuarioBinding
import com.example.bitego.viewmodels.UsuarioViewModel

class AdminEditarUsuarioFragment : Fragment() {

    private lateinit var binding: FragmentAdminEditarUsuarioBinding
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val args: AdminEditarUsuarioFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminEditarUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuario = args.usuario // Obtener usuario enviado desde el otro fragmento

        // Prellenar los campos con los datos actuales
        binding.etNombre.setText(usuario.nombre)
        binding.etApellidos.setText(usuario.apellidos)
        binding.etCorreo.setText(usuario.email)
        binding.etPassword.setText(usuario.psswd)
        binding.etCurso.setText(usuario.curso)

        // Botón para guardar cambios
        binding.btnGuardar.setOnClickListener {
            val nuevoNombre = binding.etNombre.text.toString().trim()
            val nuevosApellidos = binding.etApellidos.text.toString().trim()
            val nuevoCorreo = binding.etCorreo.text.toString().trim()
            val nuevaPassword = binding.etPassword.text.toString().trim()
            val nuevoCurso = binding.etCurso.text.toString().trim()


            if (nuevoNombre.isEmpty() || nuevoCorreo.isEmpty()) {
                Toast.makeText(requireContext(), "Los campos Nombre y Correo son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            usuarioViewModel.updateUsuario(nombre = nuevoNombre, apellidos = nuevosApellidos, email = nuevoCorreo, password = nuevaPassword, curso = nuevoCurso,)
            findNavController().navigate(R.id.action_adminEditarUsuarioFragment_to_dashboardAdminAlumno)


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
        }

        // Botón para volver atrás
        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_adminEditarUsuarioFragment_to_dashboardAdminAlumno)
        }
    }
}