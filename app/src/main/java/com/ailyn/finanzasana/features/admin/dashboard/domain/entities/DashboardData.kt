package com.ailyn.finanzasana.features.admin.dashboard.domain.entities

data class DashboardData(
    val usuariosTotales: Int,
    val montoGlobal: Double,
    val deudasVencidas: Int,
    val ultimosAbonos: List<RegistroAbonoAdmin>
)

data class RegistroAbonoAdmin(
    val nombreUsuario: String,
    val monto: Double,
    val idDeuda: Int,
    val fecha: String
)
