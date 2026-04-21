package com.ailyn.finanzasana.features.home.solicitudes.domain.usecases

import com.ailyn.finanzasana.features.home.solicitudes.domain.entities.NuevaSolicitud
import com.ailyn.finanzasana.features.home.solicitudes.domain.repositories.SolicitudRepository
import javax.inject.Inject

class CrearSolicitudUseCase @Inject constructor(
    private val repository: SolicitudRepository
) {

    suspend operator fun invoke(solicitud: NuevaSolicitud) =
        repository.crearSolicitud(solicitud)
}
