package com.example.bitego.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bitego.R
import com.example.bitego.databinding.FragmentAnyadirBocadilloBinding
import com.example.bitego.viewmodels.BocadilloViewModel


class AnyadirBocadilloFragment : Fragment() {

    private lateinit var binding: FragmentAnyadirBocadilloBinding
    private val bocadilloViewModel: BocadilloViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnyadirBocadilloBinding.inflate(inflater, container, false)
        return binding.root
    }

   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerTipo()

        binding.btnGuardar.setOnClickListener {
            guardarBocadillo()
        }

        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_adminAgregarBocadilloFragment_to_fragment_admin_bocadillo)
        }
    }

    private fun spinnerTipo() {
        val tiposBocadillo = listOf("Frío", "Caliente")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, tiposBocadillo)
        binding.spinnerTipo.adapter = adapter
    }

    private fun guardarBocadillo() {
        val nombre = binding.edtNombreBocadillo.text.toString().trim()
        val descripcion = binding.edtDescripcion.text.toString().trim()
        val tipo = binding.spinnerTipo.selectedItem.toString()
        val costeStr = binding.edtCoste.text.toString().trim()

        if (nombre.isEmpty() || descripcion.isEmpty() || costeStr.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val coste = costeStr.toDoubleOrNull()
        if (coste == null) {
            Toast.makeText(requireContext(), "El coste debe ser un número válido", Toast.LENGTH_SHORT).show()
            return
        }

        val alergenos = obtenerAlergenosSeleccionados()
        val nuevoBocadillo = Bocadillo(
            nombre = nombre,
            descripcion = descripcion,
            tipo = tipo,
            coste = coste,
            icono = "",
            dia = "",
            alergenos = alergenos
        )

        bocadilloViewModel.insertarBocadillo(nuevoBocadillo) { exito ->
            if (exito) {
                Toast.makeText(requireContext(), "Bocadillo guardado con éxito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_adminAgregarBocadilloFragment_to_fragment_admin_bocadillo)
            } else {
                Toast.makeText(requireContext(), "Error al guardar el bocadillo", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_adminAgregarBocadilloFragment_to_fragment_admin_bocadillo)
        }
    }*/

}