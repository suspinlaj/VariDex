package com.example.harrypotter.data

import java.math.BigDecimal

data class VaritaResumenDto(
    val id: Int,
    val materiales: String,
    val longitud: BigDecimal,
    val rota: String,
    val personaje: String
)
