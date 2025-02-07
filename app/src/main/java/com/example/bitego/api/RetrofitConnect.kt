package com.example.bitego.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConnect {
    const val URL_BASE = "https://app-bocatas-31889-default-rtdb.europe-west1.firebasedatabase.app/"
    val apiUsuario: ApiServiceUsuario by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceUsuario::class.java)
    }
}