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
import androidx.navigation.fragment.navArgs
import com.example.bitego.R
import com.example.bitego.databinding.FragmentAdminEditarBocadilloBinding
import com.example.bitego.databinding.FragmentAdminEditarUsuarioBinding
import com.example.bitego.modelos.Bocadillo
import com.example.bitego.viewmodels.BocadilloViewModel

class AdminEditarBocadilloFragment : Fragment() {

    private lateinit var binding:FragmentAdminEditarBocadilloBinding
    private val bocadilloViewModel: BocadilloViewModel by viewModels()
    private val args: AdminEditarBocadilloFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminEditarBocadilloBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bocadillo = args.bocadillo

        //Lista de atributos
        // Llenamos los campos con los datos del bocadillo
        binding.etBocadillo.setText(bocadillo.nombre)
        binding.etDescripcion.setText(bocadillo.descripcion)
        binding.etCoste.setText(bocadillo.coste.toString())

        // Configurar los spinners
        setupSpinner(binding.spTipo, arrayOf("Frío", "Caliente"), bocadillo.tipo)
        setupSpinner(binding.spDia, arrayOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes"), bocadillo.dia)

        binding.btnGuardar.setOnClickListener {
            actualizarBocadillo()
        }
        // Botón para volver atrás
        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_adminEditarBocadilloFragment_to_dashboardAdminBocadillo)
        }
    }
    private fun setupSpinner(spinner: Spinner, items: Array<String>, selectedValue: String) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter
        spinner.setSelection(items.indexOf(selectedValue))
    }

    private fun actualizarBocadillo() {
        val nombre = binding.etBocadillo.text.toString()
        val descripcion = binding.etDescripcion.text.toString()
        val coste = binding.etCoste.text.toString().toDoubleOrNull() ?: 0.0
        val tipo = binding.spTipo.selectedItem.toString()
        val dia = binding.spDia.selectedItem.toString()
        val alergeno = binding.etAlergenos.text.toString().trim()

        //Establecer ID
        val idAlergeno = System.currentTimeMillis().toString()

        //Inicializar el Map de alergenos
        val alerngenoEdit = mutableMapOf("1" to "Huevo")

        //agregar el nuevo alergeno
        alerngenoEdit[idAlergeno] = alergeno

        val bocadilloActualizado = Bocadillo(nombre = nombre, descripcion = descripcion, tipo = tipo, coste = coste, alergenos = alerngenoEdit, dia=dia)

        args.bocadillo.id?.let {
            bocadilloViewModel.actualizarBocadillo(it, bocadilloActualizado) { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Bocadillo actualizado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}