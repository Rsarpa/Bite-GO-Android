package com.example.bitego.fragments.alumno

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bitego.R
import com.example.bitego.adapter.CalendarioAdapter
import com.example.bitego.databinding.FragmentCalendarioBinding
import com.example.bitego.viewmodels.BocadilloViewModel

class CalendarioFragment : Fragment() {

    private var _binding: FragmentCalendarioBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarioAdapter: CalendarioAdapter
    private lateinit var bocadilloViewModel: BocadilloViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCalendarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DEBUG", "CalendarioFragment -> onViewCreated")

        try {
            // Inicializar el ViewModel
            bocadilloViewModel = ViewModelProvider(this)[BocadilloViewModel::class.java]
            Log.d("DEBUG", "ViewModel inicializado correctamente")
        } catch (e: Exception) {
            Log.e("ERROR", "Error al inicializar ViewModel", e)
        }

        // Inicializar el Adapter con una lista vacía
        calendarioAdapter = CalendarioAdapter(emptyList())
        Log.d("DEBUG", "Adaptador inicializado correctamente")

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = calendarioAdapter
        }
        Log.d("DEBUG", "RecyclerView configurado con GridLayoutManager")
        bocadilloViewModel.fetchBocadillos()

        //listar los bocadillos de toda la semana (todos)
        bocadilloViewModel.bocadillos.observe(viewLifecycleOwner) { bocadillosList ->
            Log.d("DEBUG", "Observe activado -> Se recibieron ${bocadillosList.size} bocadillos")

            if (bocadillosList.isNotEmpty()) {
                calendarioAdapter.actualizarLista(bocadillosList)
                Log.d("DEBUG", "Adaptador actualizado correctamente con ${bocadillosList.size} bocadillos")
            } else {
                Log.d("DEBUG", "Observe activado pero la lista de bocadillos está vacía")
            }
        }

        // AHORA se llama a fetchBocadillos(), asegurando que el observe ya está listo
        Log.d("DEBUG", "fetchBocadillos() ejecutado")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("DEBUG", "CalendarioFragment -> onDestroyView") // 👀 Depuración
        _binding = null
    }

}