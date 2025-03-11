package com.example.bitego.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitego.api.RetrofitConnect
import com.example.bitego.modelos.Usuario
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> get() = _isLogged

    private val _usuarioAutenticado = MutableLiveData<Usuario?>()
    val usuarioAutenticado: LiveData<Usuario?> get() = _usuarioAutenticado

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> get() = _mensaje

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    private val _errorMensaje = MutableLiveData<String>()
    val errorMensaje: LiveData<String> get() = _errorMensaje

    //guardar id del usuario
    var usuarioIdFirebase: String? = null

    //inicializar la instancia en Firebase del usuario
    init {
        _isLogged.value = auth.currentUser != null

        // Si el usuario ya está autenticado, obtener sus datos
        auth.currentUser?.email?.let { email ->
            Log.d("UsuarioViewModel", "Usuario autenticado con email: $email")
            fetchUsuarioByEmail(email)
        }
    }


    //Método que recupera todos los usuarios para el CRUD
    fun fetchUsuarios() {
        Log.d("DEBUG", "Entrando en fetchUsuarios")
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiUsuario.getUsuarios()

                //De esta manera desglosamos el MAP y devolvemos una lista de Usuarios adecuada
                response?.let { usuariosMap ->
                    val usuariosLista = usuariosMap.map { (id, usuario) ->
                        usuario.copy(uId = id) //De esta manera recuperamos el id del usuario
                    }
                    _usuarios.postValue(usuariosLista)//pasamos la lista de usuarios asignando el id
                } ?: run {
                    _errorMensaje.postValue("No se encontraron usuarios")
                }

            } catch (e: Exception) {
                _errorMensaje.postValue("Error: ${e.message}")
            }
        }
    }

    //Método para recuperar un usuario con email
    fun fetchUsuarioByEmail(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiUsuario.getUsuarios()
                val usuarioEntry = response?.entries?.find { it.value.email == email }

                if (usuarioEntry != null) {
                    val usuarioId = usuarioEntry.key
                    val usuarioEncontrado = usuarioEntry.value.copy(uId = usuarioId)

                    _usuarioAutenticado.value = usuarioEncontrado

                    //Guardar ID de Firebase
                    usuarioIdFirebase = usuarioId
                    Log.d("UsuarioViewModel", "ID de usuario obtenido: $usuarioId")
                }
            } catch (e: Exception) {
                _errorMensaje.value = "Error de la base de datos al obtener usuario: ${e.message}"
                Log.e("UsuarioViewModel", "Error en fetchUsuarioByEmail: ${e.message}")
            }
        }
    }


    fun verificarUsuario(email: String, contrasena: String){
        auth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    _isLogged.postValue(true)
                    fetchUsuarioByEmail(email)  // Cargar datos del usuario autenticado

                }else{
                    _isLogged.postValue(false)
                    Log.e("UsuarioViewModel", "Error de autenticación: ${task.exception?.message}")
                    _errorMensaje.value = "Nombre de usuario o Contraseña incorrectos"
                }
            }
    }

    //seleccionar usuario -> llamar a createUsuario .registrarUsuario
    fun insertarAlumno(usuario: Usuario,onResult: (Boolean)->Unit){
        Log.d("DEBUG", "Entrando en fetchUsuarios")
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiUsuario.createUsuario(usuario.copy(uId = null)) //Decimos que id es null para que no lo cree

                if (response.isSuccessful) {
                    onResult(true) //Insercción realizada con exito
                }else {
                    onResult(false) //Error al insertar
                }
            } catch (e: Exception) {
                _errorMensaje.postValue("Error: ${e.message}")
                onResult(false)
            }
        }
    }

    //registrar un nuevo usuario en Authentication
    fun registrarUsuario(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Usuario creado con éxito
                    val user = auth.currentUser
                    callback(true, "Usuario registrado con éxito: ${user?.email}")
                } else {
                    // Error al registrar usuario
                    callback(false, task.exception?.message)
                }
            }
    }

    //metodo para actualizar datos de un usuario
    /*fun updateUsuario(usuarioActualizado: Usuario, onResult: (Boolean) -> Unit) {
        val id = usuarioActualizado.uId

        if (id.isNullOrBlank()) {
            _errorMensaje.value = "Error: No se pudo obtener el ID del usuario"
            return
        }

        viewModelScope.launch {
            try {
                val usuarioMap = mapOf(
                    "nombre" to usuarioActualizado.nombre,
                    "apellidos" to usuarioActualizado.apellidos,
                    "email" to usuarioActualizado.email,
                    "psswrd" to usuarioActualizado.psswd,
                    "curso" to usuarioActualizado.curso,
                    "rol" to usuarioActualizado.rol
                )

                RetrofitConnect.apiUsuario.updateUsuario(id, usuarioMap)
                _mensaje.postValue("Usuario actualizado correctamente")
                fetchUsuarios() //Refrescar lista
                onResult(true)
            } catch (e: Exception) {
                _errorMensaje.postValue("Error al actualizar usuario: ${e.message}")
                onResult(false)
            }
        }
    }*/

    fun updateUsuario(nombre: String, apellidos: String, curso: String, email: String, password: String) {
        val usuario = usuarioAutenticado.value

        if (usuario == null || usuarioIdFirebase == null) {
            _errorMensaje.value = "Error: No se pudo obtener el usuario o su ID"
            return
        }

        viewModelScope.launch {
            try {
                val usuarioActualizado = usuario.copy(
                    nombre = nombre,
                    apellidos = apellidos,
                    curso = curso,
                    email = email,
                    psswd = password
                )

                val usuarioMap = mapOf(
                    "nombre" to usuarioActualizado.nombre,
                    "apellidos" to usuarioActualizado.apellidos,
                    "email" to usuarioActualizado.email,
                    "passwd" to usuarioActualizado.psswd,
                    "curso" to usuarioActualizado.curso,
                    "rol" to usuarioActualizado.rol
                )

                usuarioIdFirebase?.let { id ->
                    RetrofitConnect.apiUsuario.updateUsuario(id, usuarioMap)
                    _usuarioAutenticado.value = usuarioActualizado
                    _mensaje.value = "Perfil actualizado correctamente"
                }
            } catch (e: Exception) {
                _errorMensaje.value = "Error al actualizar perfil: ${e.message}"
            }
        }
    }


    //metodo para eliminar un usuario
    fun deleteUsuario(id: String, onResult: (Boolean) -> Unit) {
        if (id.isBlank()) {
            _errorMensaje.value = "Error: No se pudo obtener el ID del usuario"
            return
        }

        viewModelScope.launch {
            try {
                val response = RetrofitConnect.apiUsuario.deleteUsuario(id)
                if (response.isSuccessful) {
                    _mensaje.postValue("Usuario eliminado correctamente")
                    fetchUsuarios() // 🔄 Actualizar lista después de eliminar
                    onResult(true)
                } else {
                    _errorMensaje.postValue("Error al eliminar usuario")
                    onResult(false)
                }
            } catch (e: Exception) {
                _errorMensaje.postValue("Error al eliminar usuario: ${e.message}")
                onResult(false)
            }
        }
    }


    fun signOut() {
        auth.signOut()
        _isLogged.value = false // Actualiza LiveData cuando se cierra sesión
    }
}