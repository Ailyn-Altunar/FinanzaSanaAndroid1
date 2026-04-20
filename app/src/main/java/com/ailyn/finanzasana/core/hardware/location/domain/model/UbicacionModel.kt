package com.ailyn.finanzasana.core.hardware.location.domain.model

data class UbicacionModel(
    val latitud: Double,
    val longitud: Double,
    val direccion: String? = null
)
