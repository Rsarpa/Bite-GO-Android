package com.example.bitego.clases

data class Usuario (
    val uId: String,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val psswd: String,
    //todo curso ALUMNO
    val curso: String,
    val rol: String,


)