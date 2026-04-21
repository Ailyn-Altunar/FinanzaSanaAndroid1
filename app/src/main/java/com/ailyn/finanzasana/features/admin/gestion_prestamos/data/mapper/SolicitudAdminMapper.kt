package com.ailyn.finanzasana.features.admin.gestion_prestamos.data.mapper

import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.models.SolicitudAdminResponse
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin

fun SolicitudAdminResponse.toDomain() = SolicitudAdmin(
    id = id ?: 0,
    nombreUsuario = nombreUsuario,
    montoSolicitado = montoSolicitado,
    estado = estado
)
