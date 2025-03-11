package com.example.bitego.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitego.api.RetrofitConnect
import com.example.bitego.modelos.Bocadillo
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.contracts.contract

class BocadilloViewModel : ViewModel() {


    private val _bocadillos = MutableLiveData<List<Bocadillo>>()
    val bocadillos: LiveData<List<Bocadillo>> get() = _bocadillos

    private val _bocadillosCrud = MutableLiveData<List<Bocadillo>>()
    val bocadillosCrud: LiveData<List<Bocadillo>> get() = _bocadillosCrud

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> get() = _mensaje

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    /** Obtener todos los bocadillos desde la API */
    fun fetchBocadillos() {
        Log.d("DEBUG", "Obteniendo todos los bocadillos...")
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiBocadillo.getBocadillos()
                _bocadillos.value = response.values.toList()
                Log.d("DEBUG", "Bocadillos cargados: ${_bocadillos.value?.size}")
            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener bocadillos: ${e.message}"
                Log.e("ERROR", "Error al obtener bocadillos", e)
            }
        }
    }

    /** Obtener bocadillos del día actual */
    fun fetchBocadillosDia() {
        Log.d("DEBUG", "Obteniendo bocadillos del día...")
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiBocadillo.getBocadillos()
                val diaActual = obtenerDiaActual()

                val bocadillosFiltrados = response.values.filter { it.dia.equals(diaActual, ignoreCase = true) }

                _bocadillos.value = bocadillosFiltrados
                Log.d("DEBUG", "Bocadillos para $diaActual: ${bocadillosFiltrados.size}")
            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener bocadillos del día: ${e.message}"
                Log.e("ERROR", "Error al obtener bocadillos del día", e)
            }
        }
    }

    /** Obtener el día de la semana en formato de texto */
    private fun obtenerDiaActual(): String {
        val localeES = Locale("es", "ES")
        return java.text.SimpleDateFormat("EEEE", localeES).format(java.util.Date())
    }

    /*Metodo para crear un bocadillo*/
    fun insertarBocadillo(bocadillo: Bocadillo, onResult: (Boolean)->Unit){
        Log.d("DEBUG", "Entrando en fetchUsuarios")
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiBocadillo.createBocadillo(bocadillo) //Decimos que id es null para que no lo cree

                if (response.isSuccessful) {
                    _mensaje.postValue("Bocadillo creado correctamente")
                    onResult(true) //Insercción realizada con exito
                }else {
                    _mensaje.postValue("El bocadillo no puedo ser creado")
                    onResult(false) //Error al insertar
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
                onResult(false)
            }
        }
    }
    fun actualizarBocadillo(id: String, bocadillo: Bocadillo, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiBocadillo.updateBocadillo(id, bocadillo)
                onResult(response.isSuccessful)
                _mensaje.postValue("Bocadillo actualizado correctamente")
                //refrescar lista
                fetchBocadillos()
            } catch (e: Exception) {
                _errorMessage.postValue("Error al actualizar bocadillo: ${e.message}")
                onResult(false)
            }
        }
    }

    fun fetchBocadillosCrud() {
        Log.d("DEBUG", "Obteniendo todos los bocadillos para CRUD...")
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiBocadillo.getBocadillos()
                val listaBocadillosCrud = response.map { (id, bocadillo) ->
                    bocadillo.copy(id = id) // Asigna el ID sin modificar la estructura original
                }

                _bocadillosCrud.value = listaBocadillosCrud
                Log.d("DEBUG", "Bocadillos CRUD cargados: ${listaBocadillosCrud.size}")
            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener bocadillos CRUD: ${e.message}"
                Log.e("ERROR", "Error al obtener bocadillos CRUD", e)
            }
        }
    }


    fun eliminarBocadillo(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiBocadillo.deleteBocadillo(id)
                if (response.isSuccessful){
                    _mensaje.postValue("Bocadillo eliminado con éxito")
                    onResult(response.isSuccessful)
                }else{
                    _errorMessage.postValue("Error al eliminar el bocadillo")
                    onResult(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener bocadillos CRUD: ${e.message}"
                onResult(false)
            }
        }
    }
}