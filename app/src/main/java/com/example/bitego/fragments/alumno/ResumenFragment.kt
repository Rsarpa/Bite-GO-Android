package com.example.bitego.fragments.alumno

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bitego.R
import com.example.bitego.databinding.FragmentResumenBinding
import com.example.bitego.viewmodels.PedidoViewModel
import com.example.bitego.viewmodels.UsuarioViewModel

class ResumenFragment : Fragment() {

    private var _binding: FragmentResumenBinding? = null
    private val binding get() = _binding!!

    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val pedidoViewModel: PedidoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resumen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioViewModel.usuarioAutenticado.observe(viewLifecycleOwner) { usuario ->
            usuario?.let {
                it.uId?.let { it1 -> pedidoViewModel.obtenerPedidoDelDia(it1) }
            }
        }

        pedidoViewModel.pedidoDelDia.observe(viewLifecycleOwner) { pedido ->
            if (pedido != null) {
                binding.nombreBocadillo.text = pedido.bocadillo.nombre
                binding.costeBocadillo.text = "${pedido.precio}€"
                binding.imgBocadillo.setImageResource(R.drawable.sandwich)

                binding.btnCancelarPedido.setOnClickListener {
                    pedidoViewModel.cancelarPedido()
                    findNavController().navigate(R.id.action_resumenFragment_to_fragment_alumno) // 🔥 Ahora vuelve a PedidoFragment
                }

                binding.btVolver.setOnClickListener {
                    findNavController().navigate(R.id.action_resumenFragment_to_fragment_alumno)
                }
            }
        }
    }
}