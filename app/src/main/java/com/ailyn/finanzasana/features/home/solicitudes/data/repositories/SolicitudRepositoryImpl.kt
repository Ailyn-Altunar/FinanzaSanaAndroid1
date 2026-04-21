package com.ailyn.finanzasana.features.home.solicitudes.data.repositories

import com.ailyn.finanzasana.core.session.SessionManager
import com.ailyn.finanzasana.features.home.solicitudes.data.datasource.api.SolicitudApi
import com.ailyn.finanzasana.features.home.solicitudes.data.models.CrearSolicitudRequestDto
import com.ailyn.finanzasana.features.home.solicitudes.data.models.SolicitudPrestamoDto
import com.ailyn.finanzasana.features.home.solicitudes.domain.entities.NuevaSolicitud
import com.ailyn.finanzasana.features.home.solicitudes.domain.repositories.SolicitudRepository
import javax.inject.Inject

class SolicitudRepositoryImpl @Inject constructor(
    private val api: SolicitudApi,
    private val sessionManager: SessionManager
) : SolicitudRepository {

    override suspend fun crearSolicitud(solicitud: NuevaSolicitud): SolicitudPrestamoDto? {
        return try {
            val token = sessionManager.getToken() ?: return null

            val request = CrearSolicitudRequestDto(
                idUsuario = solicitud.idUsuario,
                idEmpresa = solicitud.idEmpresa,
                montoSolicitado = solicitud.montoSolicitado,
                meses = solicitud.meses,
                motivo = solicitud.motivo,
                idCategoria = solicitud.idCategoria,
                imagenBase64 = solicitud.imagenBase64,
                latitud = solicitud.latitud,
                longitud = solicitud.longitud
            )

            api.crearSolicitud(
                token = "Bearer $token",
                request = request
            )
        } catch (e: Exception) {
            null
        }
    }
}
