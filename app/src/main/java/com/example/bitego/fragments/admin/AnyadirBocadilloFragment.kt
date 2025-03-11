package com.example.bitego.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bitego.R
import com.example.bitego.databinding.FragmentAnyadirBocadilloBinding
import com.example.bitego.modelos.Bocadillo
import com.example.bitego.viewmodels.BocadilloViewModel

class AnyadirBocadilloFragment : Fragment() {

    private lateinit var binding: FragmentAnyadirBocadilloBinding
    private val bocadilloViewModel: BocadilloViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnyadirBocadilloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Llenar los arrays del spinner correctamente
        setupSpinner(binding.spTipo, arrayOf("Caliente", "Frio"))  // Fix: separa bien las opciones
        setupSpinner(binding.spDia, arrayOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes"))

        // Mostrar mensajes de éxito
        bocadilloViewModel.mensaje.observe(viewLifecycleOwner) { mensaje ->
            if (!mensaje.isNullOrEmpty()) {
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            }
        }

        // Mostrar mensajes de error
        bocadilloViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGuardar.setOnClickListener {
            guardarBocadillo()
        }

        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_anyadirBocadilloFragment_to_dashboardAdminBocadillo)
        }
    }

    private fun setupSpinner(spinner: Spinner, items: Array<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter
    }

    private fun guardarBocadillo() {
        val nombre = binding.etBocadillo.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()
        val tipo = binding.spTipo.selectedItem.toString()
        val costeStr = binding.etCoste.text.toString().trim()
        val alergeno = binding.etAlergenos.text.toString().trim()

        val idAlergeno = System.currentTimeMillis().toString()

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || descripcion.isEmpty() || costeStr.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que el coste sea un número válido
        val coste = costeStr.toDoubleOrNull()
        if (coste == null) {
            Toast.makeText(requireContext(), "El coste debe ser un número válido", Toast.LENGTH_SHORT).show()
            return
        }

        // Inicializar el mapa de alérgenos
        val nuevoAlergeno = mutableMapOf(idAlergeno to alergeno)

        // Crear y guardar el bocadillo
        val nuevoBocadillo = Bocadillo(nombre = nombre, descripcion = descripcion, tipo = tipo, coste = coste, dia = "", alergenos = nuevoAlergeno)

        bocadilloViewModel.insertarBocadillo(nuevoBocadillo) { exito ->
            if (exito) {
                findNavController().navigate(R.id.action_anyadirBocadilloFragment_to_dashboardAdminBocadillo)
            }
        }
    }
}
