package com.ailyn.finanzasana.features.admin.gestion_prestamos.data.repositories

import com.ailyn.finanzasana.core.database.dao.SolicitudDao
import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.datasource.api.PrestamosAdminApi
import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.mapper.toAdminDomain
import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.mapper.toDomain
import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.mapper.toEntity
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.repositories.PrestamosAdminRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PrestamosAdminRepositoryImpl @Inject constructor(
    private val api: PrestamosAdminApi,
    private val dao: SolicitudDao
) : PrestamosAdminRepository {

    override suspend fun getSolicitudesPendientes(): List<SolicitudAdmin> {
        return try {
            val dtos = api.getSolicitudesPendientes()
            try {
                dao.deleteSolicitudesByEstado(1)
                dao.insertSolicitudes(dtos.map { it.toEntity() })
            } catch (e: Exception) { }
            
            dtos.map { it.toDomain() }
        } catch (e: Exception) {
            val cached = dao.getSolicitudesByEstado(1).first()
            if (cached.isNotEmpty()) cached.map { it.toAdminDomain() } else throw e
        }
    }

    override suspend fun getHistorialSolicitudes(): List<SolicitudAdmin> {
        return try {
            val dtos = api.getHistorialSolicitudes()
            try {
                dao.deleteSolicitudesByEstado(2)
                dao.deleteSolicitudesByEstado(3)
                dao.insertSolicitudes(dtos.map { it.toEntity() })
            } catch (e: Exception) {  }
            
            dtos.map { it.toDomain() }
        } catch (e: Exception) {
            val cached = dao.getAllSolicitudes().first().filter { it.estado != 1 }
            if (cached.isNotEmpty()) cached.map { it.toAdminDomain() } else throw e
        }
    }

    override suspend fun aprobarSolicitud(id: Int) {
        api.aprobarSolicitud(id)
        try { dao.deleteAllSolicitudes() } catch(e: Exception) {}
    }

    override suspend fun rechazarSolicitud(id: Int) {
        api.rechazarSolicitud(id)
        try { dao.deleteAllSolicitudes() } catch(e: Exception) {}
    }
}
