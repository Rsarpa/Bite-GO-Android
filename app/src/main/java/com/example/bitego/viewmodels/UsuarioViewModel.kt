package com.example.bitego.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitego.api.RetrofitConnect
import com.example.bitego.clases.Usuario
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> get() = _isLogged

    /*fun fetchUsuarios(){

        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiUsuario.getUsers()
                _listaUsuarios.value = response
            }catch (e: Exception){
                Log.e("Error en el fetch: ${e.localizedMessage}")
            }
        }
    }*/

    fun verificarUsuario(email: String, contrasena: String){
        auth. signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    _isLogged.value = true
                }else{
                    _isLogged.value = false
                    Log.e("UsuarioViewModel", "Error de autenticación: ${task.exception?.message}")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _isLogged.value = false // Actualiza LiveData cuando se cierra sesión
    }
}