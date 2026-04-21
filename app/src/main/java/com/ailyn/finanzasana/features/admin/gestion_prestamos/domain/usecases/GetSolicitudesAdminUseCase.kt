package com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.usecases

import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.repositories.PrestamosAdminRepository
import javax.inject.Inject

class GetSolicitudesAdminUseCase @Inject constructor(
    private val repository: PrestamosAdminRepository
) {
    suspend fun getPendientes(): List<SolicitudAdmin> = repository.getSolicitudesPendientes()
    
    suspend fun getHistorial(): List<SolicitudAdmin> = repository.getHistorialSolicitudes()
}
