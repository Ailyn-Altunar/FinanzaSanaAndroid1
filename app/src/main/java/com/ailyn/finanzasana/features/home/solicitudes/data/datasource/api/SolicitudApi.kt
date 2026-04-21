package com.ailyn.finanzasana.features.home.solicitudes.data.datasource.api

import com.ailyn.finanzasana.features.home.solicitudes.data.models.CrearSolicitudRequestDto
import com.ailyn.finanzasana.features.home.solicitudes.data.models.SolicitudPrestamoDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SolicitudApi {

    @POST("solicitudes")
    suspend fun crearSolicitud(
        @Header("Authorization") token: String,
        @Body request: CrearSolicitudRequestDto
    ): SolicitudPrestamoDto
}
