package com.ailyn.finanzasana.features.home.solicitudes.domain.entities

data class NuevaSolicitud(
    val idUsuario: Int,
    val idEmpresa: Int,
    val montoSolicitado: Double,
    val meses: Int,
    val motivo: String,
    val tasaInteres: Double,
    val idCategoria: Int,
    val imagenBase64: String?,
    val latitud: Double?,
    val longitud: Double?
)
