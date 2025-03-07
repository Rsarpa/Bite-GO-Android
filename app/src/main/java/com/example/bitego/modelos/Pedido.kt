package com.example.bitego.modelos

import com.google.firebase.Timestamp

data class Pedido (
    val id: String,
    val id_usuario: String,
    val bocadillo: Bocadillo,
    val fecha_hora: Timestamp,
    val estado:Boolean,
    val precio:Double)