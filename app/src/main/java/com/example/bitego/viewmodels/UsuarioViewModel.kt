package com.example.bitego.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitego.api.RetrofitConnect
import com.example.bitego.clases.Usuario
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    private val _listaUsuarios = MutableLiveData<Map<String,Usuario>>()
    val listaUsuario: LiveData<Map<String,Usuario>> get() = _listaUsuarios

    fun fetchUsuarios(){

        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiUsuario.getUsers()
                _listaUsuarios.value = response
            }catch ()
        }
    }
}