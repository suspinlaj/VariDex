package com.example.harrypotter.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class VaritaDto(
    val madera: String,
    val nucleo: String,
    val longitud: BigDecimal,
    val rota: Boolean,
    val idPersonaje: Int?,
    val personaje: String?
)
