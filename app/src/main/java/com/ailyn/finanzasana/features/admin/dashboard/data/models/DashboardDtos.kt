package com.ailyn.finanzasana.features.admin.dashboard.data.models

import com.google.gson.annotations.SerializedName

// Respuesta de /admin/metrics
data class MetricsResponseDto(
    @SerializedName("usuariosTotales") val usuariosTotales: Int,
    @SerializedName("montoGlobal") val montoGlobal: Double,
    @SerializedName("deudasVencidas") val deudasVencidas: Int
)

// Respuesta de /admin/actividad (es una lista)
data class RegistroAbonoDto(
    @SerializedName("nombreUsuario") val nombreUsuario: String,
    @SerializedName("montoAbono") val montoAbono: Double,
    @SerializedName("idDeuda") val idDeuda: Int,
    @SerializedName("fecha") val fecha: String
)
