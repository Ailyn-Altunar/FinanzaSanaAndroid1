package com.ailyn.finanzasana.core.hardware.location.domain

import com.ailyn.finanzasana.core.hardware.location.domain.model.UbicacionModel

interface LocationManager {
    suspend fun obtenerUbicacion(): UbicacionModel?
    fun tienePermisoUbicacion(): Boolean
}
