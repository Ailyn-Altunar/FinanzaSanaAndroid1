package com.ailyn.finanzasana.features.admin.gestion_prestamos.data.repositories

import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.datasource.api.PrestamosAdminApi
import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.mapper.toDomain
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.repositories.PrestamosAdminRepository
import javax.inject.Inject

class PrestamosAdminRepositoryImpl @Inject constructor(
    private val api: PrestamosAdminApi
) : PrestamosAdminRepository {
    override suspend fun getSolicitudesPendientes(): List<SolicitudAdmin> =
        api.getSolicitudesPendientes().map { it.toDomain() }

    override suspend fun getHistorialSolicitudes(): List<SolicitudAdmin> =
        api.getHistorialSolicitudes().map { it.toDomain() }

    override suspend fun aprobarSolicitud(id: Int) = api.aprobarSolicitud(id)

    override suspend fun rechazarSolicitud(id: Int) = api.rechazarSolicitud(id)
}
