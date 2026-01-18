package com.example.harrypotter.network

import com.example.harrypotter.data.CrearVaritaDto
import com.example.harrypotter.data.PersonajeDto
import com.example.harrypotter.data.VaritaDto
import com.example.harrypotter.data.VaritaResumenDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("varita/resumen")
    suspend fun getVaritas(): List<VaritaResumenDto>

    @PUT("varita/romper/{id}")
    suspend fun romperVarita(@Path("id") id: Int): VaritaResumenDto

    @POST("varita")
    suspend fun crearVarita(
        @Body crearVaritaDto: CrearVaritaDto
    ): VaritaResumenDto

    // Buscar personaje por nombre (devuelve una lista)
    @GET("nombre/{palabra}")
    suspend fun buscarPersonajePorNombre(@Path("palabra") nombre: String): List<PersonajeDto>

    // Asignar la varita al personaje
    @PUT("{idPersonaje}/varita/{idVarita}")
    suspend fun asignarVarita(
        @Path("idPersonaje") idPersonaje: Int,
        @Path("idVarita") idVarita: Int
    ): VaritaDto

}