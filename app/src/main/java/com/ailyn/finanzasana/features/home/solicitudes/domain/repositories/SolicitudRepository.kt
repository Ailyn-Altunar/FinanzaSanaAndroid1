package com.ailyn.finanzasana.features.home.solicitudes.domain.repositories

import com.ailyn.finanzasana.features.home.solicitudes.domain.entities.NuevaSolicitud
import com.ailyn.finanzasana.features.home.solicitudes.data.models.SolicitudPrestamoDto

interface SolicitudRepository {

    suspend fun crearSolicitud(solicitud: NuevaSolicitud): SolicitudPrestamoDto?
}
