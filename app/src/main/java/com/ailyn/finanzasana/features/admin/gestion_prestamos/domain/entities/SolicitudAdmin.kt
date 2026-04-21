package com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities

data class SolicitudAdmin(
    val id: Int,
    val nombreUsuario: String,
    val montoSolicitado: Double,
    val estado: String // "PENDIENTE", "APROBADA", "RECHAZADA"
)
