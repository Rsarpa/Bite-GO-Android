package com.example.bitego.fragments.alumno

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitego.R
import com.example.bitego.adapter.BocadilloProvider
import com.example.bitego.adapter.PedirBocadilloAdapter
import com.example.bitego.databinding.ControlPanelBinding
import com.example.bitego.databinding.FragmentAlumnoBinding

class AlumnoFragment : Fragment() {


    private var _binding : FragmentAlumnoBinding? = null
    private val binding get() = _binding!!

    //private val bocadilloViewModel: BocadilloViewModel by viewModels()
    //private lateinit var bocadilloAdapter: BocadilloDiaAdapter

    private var bocadilloSeleccionado: String? = null // Guardar el nombre del bocadillo seleccionado

    //todo obtener bocadillo desde la api


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlumnoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirmar.visibility = View.GONE

        /*bocadilloAdapter = BocadilloDiaAdapter(emptyList()) { bocadillo ->
            bocadilloSeleccionado = bocadillo.nombre
            binding.btnConfirmar.visivility = View.VISIBLE

             binding.recyclerBocadillos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bocadilloAdapter
        }

        bocadilloViewModel.fetchBocadillosDia()

        bocadilloViewModel.bocadillos.observe(viewLifecycleOwner, Observer { bocadillosList ->
            bocadilloAdapter.actualizarLista(bocadillosList)
        })
        Log.d("DEBUG", "RecyclerView después de asignar datos: ${bocadilloAdapter.itemCount}")

            */

        binding.btnConfirmar.setOnClickListener{
            //todo guardar pedido selección
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    //todo cambiar al activity
    /*private lateinit var binding: ControlPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       binding = ControlPanelBinding.inflate(layoutInflater)
       setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerBocadillos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bocadilloProvider = BocadilloProvider(this)
        val listaBocadillo = bocadilloProvider.obtenerBocadillo()

        recyclerView.adapter = PedirBocadilloAdapter(listaBocadillo)
    }*/

}