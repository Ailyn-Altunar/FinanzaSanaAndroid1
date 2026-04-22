package com.ailyn.finanzasana.features.admin.gestion_prestamos.data.mapper

import com.ailyn.finanzasana.core.database.entities.SolicitudEntity
import com.ailyn.finanzasana.features.admin.gestion_prestamos.data.models.SolicitudAdminResponse
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin

fun SolicitudAdminResponse.toDomain() = SolicitudAdmin(
    id = id,
    nombreUsuario = nombreUsuario,
    nombreEmpresa = nombreEmpresa,
    montoSolicitado = montoSolicitado,
    estado = estado
)

fun SolicitudAdminResponse.toEntity() = SolicitudEntity(
    id = id,
    nombreUsuario = nombreUsuario,
    nombreEmpresa = nombreEmpresa,
    montoSolicitado = montoSolicitado,
    meses = 0,
    motivo = "",
    tasaInteres = 0.0,
    estado = when(estado.uppercase()) {
        "PENDIENTE" -> 1
        "APROBADA" -> 2
        "RECHAZADA" -> 3
        else -> 0
    },
    fechaSolicitud = "",
    categoriaId = 0
)

fun SolicitudEntity.toAdminDomain() = SolicitudAdmin(
    id = id,
    nombreUsuario = nombreUsuario,
    nombreEmpresa = nombreEmpresa,
    montoSolicitado = montoSolicitado,
    estado = when(estado) {
        1 -> "PENDIENTE"
        2 -> "APROBADA"
        3 -> "RECHAZADA"
        else -> "DESCONOCIDO"
    }
)
