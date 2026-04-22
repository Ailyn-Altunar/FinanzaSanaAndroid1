package com.ailyn.finanzasana.features.home.solicitudes.data.repositories

import com.ailyn.finanzasana.core.database.dao.SolicitudDao
import com.ailyn.finanzasana.core.database.entities.SolicitudEntity
import com.ailyn.finanzasana.core.session.SessionManager
import com.ailyn.finanzasana.features.home.solicitudes.data.datasource.api.SolicitudApi
import com.ailyn.finanzasana.features.home.solicitudes.data.models.CrearSolicitudRequestDto
import com.ailyn.finanzasana.features.home.solicitudes.data.models.SolicitudPrestamoDto
import com.ailyn.finanzasana.features.home.solicitudes.domain.entities.NuevaSolicitud
import com.ailyn.finanzasana.features.home.solicitudes.domain.repositories.SolicitudRepository
import javax.inject.Inject

class SolicitudRepositoryImpl @Inject constructor(
    private val api: SolicitudApi,
    private val dao: SolicitudDao,
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

            val response = api.crearSolicitud(
                token = "Bearer $token",
                request = request
            )

            response?.let {
                try {
                    dao.insertSolicitudes(listOf(
                        SolicitudEntity(
                            id = it.id,
                            nombreUsuario = "Mi Solicitud", 
                            nombreEmpresa = "Empresa",
                            montoSolicitado = it.montoSolicitado,
                            meses = it.meses,
                            motivo = it.motivo,
                            tasaInteres = it.tasaInteres,
                            estado = 1,
                            fechaSolicitud = it.fechaSolicitud,
                            categoriaId = it.idCategoria
                        )
                    ))
                } catch (e: Exception) {  }
            }

            return response
        } catch (e: Exception) {
            null
        }
    }
}
