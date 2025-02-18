package com.example.bitego.api

import com.example.bitego.clases.Usuario
import retrofit2.http.GET

interface ApiServiceUsuario {
    @GET("usuarios.json")
    suspend fun getUsers(): Map<String, Usuario>
}