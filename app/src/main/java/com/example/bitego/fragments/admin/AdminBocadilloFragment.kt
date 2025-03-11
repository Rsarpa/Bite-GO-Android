package com.example.bitego.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitego.R
import com.example.bitego.adapter.BocadilloAdapter
import com.example.bitego.databinding.FragmentAdminAlumnoBinding
import com.example.bitego.databinding.FragmentAdminBocadilloBinding
import com.example.bitego.modelos.Bocadillo
import com.example.bitego.modelos.Usuario
import com.example.bitego.viewmodels.BocadilloViewModel

class AdminBocadilloFragment : Fragment() {

    private var _binding: FragmentAdminBocadilloBinding? = null
    private val binding get() = _binding!!

    private val bocadilloViewModel: BocadilloViewModel by viewModels()
    private lateinit var bocadilloAdapter: BocadilloAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminBocadilloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bocadilloViewModel.fetchBocadillosCrud()

        //  Usar binding.recyclerViewAlumnos en lugar de findViewById
        binding.rvBocadillos.layoutManager = LinearLayoutManager(requireContext())
        bocadilloAdapter = BocadilloAdapter(
            listaBocadillos = emptyList(),
            onEditarClick = { bocadillo -> irAEditarBocadillo(bocadillo) }, // Pasa el bocadillo correcto
            onEliminarClick = { bocadillo -> eliminarBocadillo(bocadillo) }  // Ahora permite eliminar
        )
        binding.rvBocadillos.adapter = bocadilloAdapter


        bocadilloViewModel.bocadillosCrud.observe(viewLifecycleOwner) { bocadillos ->
            if (!bocadillos.isNullOrEmpty()) {
                bocadilloAdapter.actualizarLista(bocadillos)
            } else {
                Toast.makeText(requireContext(), "No hay datos", Toast.LENGTH_SHORT).show()
            }
        }

        bocadilloViewModel.errorMessage.observe(viewLifecycleOwner) { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
        }

        binding.btnAnyadir.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardAdminBocadillo_to_anyadirBocadilloFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evitar fugas de memoria
    }

    private fun irAEditarBocadillo(bocadillo: Bocadillo) {
        val action = AdminBocadilloFragmentDirections
            .actionDashboardAdminBocadilloToAdminEditarBocadilloFragment(bocadillo)
        findNavController().navigate(action)
    }

    private fun eliminarBocadillo(bocadillo: Bocadillo) {
        bocadillo.id?.let {
            bocadilloViewModel.eliminarBocadillo(it) { exito ->
                if (exito) {
                    Toast.makeText(requireContext(), "Bocadillo eliminado", Toast.LENGTH_SHORT).show()
                    bocadilloViewModel.fetchBocadillosCrud()
                } else {
                    Toast.makeText(requireContext(), "Error al eliminar bocadillo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}