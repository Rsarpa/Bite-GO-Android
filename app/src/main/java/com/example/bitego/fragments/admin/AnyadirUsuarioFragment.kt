package com.example.bitego.fragments.admin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bitego.R
import com.example.bitego.databinding.FragmentAnyadirUsuarioBinding
import com.example.bitego.modelos.Usuario
import com.example.bitego.viewmodels.UsuarioViewModel

class AnyadirUsuarioFragment : Fragment() {

    private lateinit var binding: FragmentAnyadirUsuarioBinding
    private val usuarioViewModel: UsuarioViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnyadirUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lista de roles
        val roles = listOf("Alumno", "Cocina", "Admin")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, roles)
        binding.spRol.adapter = adapter

        // Deshabilitar botón inicialmente
        binding.btnGuardar.isEnabled = false

        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_anyadirUsuarioFragment_to_dashboardAdminAlumno)
        }

        binding.btnGuardar.setOnClickListener {
            guardarAlumno()
        }

        // Configurar validaciones en tiempo real
        setupValidations()
    }

    private fun setupValidations() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validarFormulario()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etCorreo.addTextChangedListener(textWatcher)
        binding.etPassword.addTextChangedListener(textWatcher)
        binding.etNombre.addTextChangedListener(textWatcher)
    }

    private fun validarFormulario() {
        val nombre = binding.etNombre.text.toString().trim()
        val correo = binding.etCorreo.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        val emailValido = Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        val passwordValida = password.length >= 6
        val camposNoVacios = nombre.isNotEmpty() && correo.isNotEmpty() && password.isNotEmpty()

        // Mostrar errores en tiempo real
        if (!emailValido) {
            binding.etCorreo.error = "Correo inválido"
        } else {
            binding.etCorreo.error = null
        }

        if (!passwordValida) {
            binding.etPassword.error = "Debe tener más de 6 caracteres"
        } else {
            binding.etPassword.error = null
        }

        // Habilitar o deshabilitar el botón
        binding.btnGuardar.isEnabled = emailValido && passwordValida && camposNoVacios
    }

    private fun guardarAlumno() {
        val nombre = binding.etNombre.text.toString().trim()
        val apellidos = binding.etApellidos.text.toString().trim()
        val correo = binding.etCorreo.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val curso = binding.etCurso.text.toString().trim()
        val rol = binding.spRol.selectedItem.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(requireContext(), "Correo inválido", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length <= 6) {
            Toast.makeText(requireContext(), "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevoUsuario = Usuario(uId = null, nombre = nombre, apellidos = apellidos, email = correo, psswd = password, curso = curso, rol = rol)

        //Registra alumnos en el auth
        usuarioViewModel.insertarAlumno(nuevoUsuario) { exito ->
            if (exito) {
                Toast.makeText(requireContext(), "Alumno guardado con éxito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_anyadirUsuarioFragment_to_dashboardAdminAlumno)
                nuevoUsuario.email?.let {
                    nuevoUsuario.psswd?.let { it1 ->
                        usuarioViewModel.registrarUsuario(it, it1) { success, message ->
                            if (success) {
                                Log.d("FirebaseAuth", message ?: "Registro exitoso")
                            } else {
                                Log.e("FirebaseAuth", "Error al registrar usuario: $message")
                            }
                        }
                    }
                }

            } else {
                Toast.makeText(requireContext(), "Error al guardar el alumno", Toast.LENGTH_SHORT).show()
            }
        }
    }
}