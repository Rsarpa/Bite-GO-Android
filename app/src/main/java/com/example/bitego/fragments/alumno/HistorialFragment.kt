package com.example.bitego.fragments.alumno

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitego.R
import com.example.bitego.adapter.PedidoAdapter
import com.example.bitego.databinding.FragmentHistorialBinding
import com.example.bitego.viewmodels.PedidoViewModel
import com.example.bitego.viewmodels.UsuarioViewModel


class HistorialFragment : Fragment() {
    private var _binding: FragmentHistorialBinding? = null
    private val binding get() = _binding!!

    private val pedidoViewModel: PedidoViewModel by viewModels()
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    private lateinit var pedidoAdapter: PedidoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pedidoAdapter = PedidoAdapter(emptyList())
        binding.rwPedidos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pedidoAdapter
        }

        usuarioViewModel.usuarioAutenticado.observe(viewLifecycleOwner) { usuario ->
            usuario?.let {
                it.uId?.let { it1 -> pedidoViewModel.obtenerPedidosAlumno(it1) }
            }
        }

        pedidoViewModel.pedidosAlumno.observe(viewLifecycleOwner) { pedidos ->
            pedidoAdapter.actualizarLista(pedidos)
        }

        pedidoViewModel.totalBocadillos.observe(viewLifecycleOwner) { total ->
            binding.totalBocadillos.text = total.toString()
        }

        pedidoViewModel.totalGastado.observe(viewLifecycleOwner) { total ->
            binding.totalGastado.text = "%.2f€".format(total)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}