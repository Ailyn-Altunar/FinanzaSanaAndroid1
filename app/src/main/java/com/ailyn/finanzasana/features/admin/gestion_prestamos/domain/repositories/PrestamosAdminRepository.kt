package com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.repositories

import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin

interface PrestamosAdminRepository {
    suspend fun getSolicitudesPendientes(): List<SolicitudAdmin>
    suspend fun getHistorialSolicitudes(): List<SolicitudAdmin>
    suspend fun aprobarSolicitud(id: Int)
    suspend fun rechazarSolicitud(id: Int)
}
