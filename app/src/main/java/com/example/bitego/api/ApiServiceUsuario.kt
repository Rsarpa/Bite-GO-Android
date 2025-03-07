package com.example.bitego.api

import com.example.bitego.modelos.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceUsuario {
    @GET("usuarios.json")
    suspend fun getUsuarios(): Map<String, Usuario>

    // Guardar un nuevo usuario
    @POST("usuarios.json")
    suspend fun createUsuario(@Body usuario: Usuario): Response<Usuario>

    // Actualizar solo algunos campos del usuario (PATCH)
    @PUT("usuarios/{id}.json")  //Permite actualizar un usuario
    suspend fun updateUsuario(@Path("id") id: String, @Body usuario: Map<String, String?>)

    // Eliminarr un POST
    @DELETE("usuarios/{id}.json")
    suspend fun deleteUsuario(@Path("id")id: String): Response<Unit> //Usamos Response Unit en DELETE por que devuelve un 204 No Content

}