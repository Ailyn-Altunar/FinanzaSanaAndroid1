package com.ailyn.finanzasana.features.admin.gestion_prestamos.data.mapper

import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.models.SolicitudAdminResponse
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin

fun SolicitudAdminResponse.toDomain() = SolicitudAdmin(
    id = id,
    nombreUsuario = nombreUsuario,
    nombreEmpresa = nombreEmpresa,
    montoSolicitado = montoSolicitado,
    estado = estado
)
