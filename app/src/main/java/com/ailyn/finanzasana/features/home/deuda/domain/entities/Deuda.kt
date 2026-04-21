package com.ailyn.finanzasana.features.home.deuda.domain.entities

data class Deuda(
    val id: Int? = null,
    val concepto: String,
    val montoOriginal: Double,
    val saldoActual: Double,
    val porcentajePagado: Double,
    val fechaVencimiento: String,
    val tasaInteres: Double,
    val categoriaNombre: String,
    val idUsuario: Int,
    val estado: String,
    val imagenBase64: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null,
    val abonos: List<Abono> = emptyList()
)
