package com.example.bitego.fragments.alumno

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.R
import com.example.bitego.adapter.BocadilloDia
import com.example.bitego.databinding.FragmentAlumnoBinding
import com.example.bitego.modelos.Bocadillo
import com.example.bitego.util.ConfirmarEvento
import com.example.bitego.viewmodels.BocadilloViewModel
import com.example.bitego.viewmodels.PedidoViewModel
import com.example.bitego.viewmodels.UsuarioViewModel

class AlumnoFragment : Fragment() {

    private var _binding: FragmentAlumnoBinding? = null
    private val binding get() = _binding!!

    private val bocadilloViewModel: BocadilloViewModel by viewModels()
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val pedidoViewModel: PedidoViewModel by viewModels()

    private lateinit var cuadroPedido: LinearLayout

    private lateinit var bocadilloAdapter: BocadilloDia

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlumnoBinding.inflate(inflater, container, false)
        // Obtener los bocadillos de cada día

        bocadilloViewModel.fetchBocadillosDia()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Creamos el adaptador solo una vez
        bocadilloAdapter = BocadilloDia(emptyList()) { bocadillo ->
            // Mostrar el cuadro de confirmación cuando se selecciona un bocadillo
            ConfirmarEvento(onConfirm = {
                // Observar el usuarioId una sola vez
                val idUsuario = usuarioViewModel.usuarioIdFirebase

                if (idUsuario.isNullOrBlank()) {
                    Log.d("UsuarioId", "ID de usuario null")
                    return@ConfirmarEvento
                } else {
                    // Aquí, cuando el usuario confirma el pedido, comprobamos si ya tiene un pedido
                    pedidoViewModel.obtenerPedidosAlumno(idUsuario)

                    // Observar el pedido del día solo en este punto
                    pedidoViewModel.pedidoDelDia.observe(viewLifecycleOwner, Observer { pedido ->
                        if (pedido != null) {
                            // Si el usuario ya tiene un pedido, mostramos un mensaje
                            Toast.makeText(requireContext(), "Ya tienes un pedido para hoy.", Toast.LENGTH_SHORT).show()
                        } else {
                            // Si no tiene un pedido, realizamos el nuevo pedido
                            pedidoViewModel.realizarPedido(idUsuario, bocadillo)
                            binding.cuadroPedido.visibility = View.VISIBLE

                            // Mostrar mensaje de éxito y actualizar la UI
                            Toast.makeText(requireContext(), "Pedido realizado con éxito", Toast.LENGTH_SHORT).show()

                            // Mostrar cuadro de pedido
                            binding.cuadroPedido.visibility = View.VISIBLE
                        }
                    })
                }

            }).show(parentFragmentManager, "ConfirmDialog")
        }

        // Configurar el RecyclerView con el adaptador
        binding.recyclerBocadillos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bocadilloAdapter
        }

        // Observar los bocadillos desde el ViewModel
        bocadilloViewModel.bocadillos.observe(viewLifecycleOwner, Observer { bocadilloLista ->
            bocadilloAdapter.actualizarLista(bocadilloLista)
        })

        // Recibir los mensajes de error del ViewModel
        pedidoViewModel.errorMensaje.observe(viewLifecycleOwner, Observer { mensaje ->
            mensaje?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        // Recibir los mensajes de éxito del ViewModel
        pedidoViewModel.mensaje.observe(viewLifecycleOwner, Observer { mensaje ->
            mensaje?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        // Botón para ver el pedido en ResumenFragment
        binding.btnVerPedido.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_alumno_to_resumenFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
