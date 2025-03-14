package com.example.bitego.modelos

import java.io.Serializable

data class Usuario (
    val uId: String? = null,
    val nombre: String? = null,
    val apellidos: String? = null,
    val email: String? = null,
    val psswd: String? = null,
    val curso: String? = null,
    val rol: String? = null
):Serializable