package com.example.bitego.api

import com.example.bitego.modelos.Pedido
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServicePedido {
    @GET("pedidos.json")
    suspend fun getPedidos(): Map<String, Pedido>?

    @POST("pedidos.json")
    suspend fun realizarPedido(@Body pedido: Pedido): Response<Void>

    @DELETE("pedidos/{id}.json")
    suspend fun cancelarPedido(@Path("id") id: String): Response<Void>
}