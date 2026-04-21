package com.ailyn.finanzasana.features.admin.gestion_prestamos.data.datasource.api

import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.models.SolicitudAdminResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PrestamosAdminApi {

    @GET("/solicitudes/pendientes")
    suspend fun getSolicitudesPendientes(): List<SolicitudAdminResponse>

    @GET("/solicitudes/historial")
    suspend fun getHistorialSolicitudes(): List<SolicitudAdminResponse>

    @POST("/solicitudes/{id}/aprobar")
    suspend fun aprobarSolicitud(@Path("id") id: Int)

    @POST("/solicitudes/{id}/rechazar")
    suspend fun rechazarSolicitud(@Path("id") id: Int)
}
