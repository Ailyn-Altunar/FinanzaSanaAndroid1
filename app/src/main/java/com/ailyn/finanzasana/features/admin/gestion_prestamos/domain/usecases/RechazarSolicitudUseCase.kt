package com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.usecases

import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.repositories.PrestamosAdminRepository
import javax.inject.Inject

class RechazarSolicitudUseCase @Inject constructor(
    private val repository: PrestamosAdminRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.rechazarSolicitud(id)
    }
}
