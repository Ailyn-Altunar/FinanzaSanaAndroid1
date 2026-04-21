package com.ailyn.finanzasana.features.home.empresas.data.models

import kotlinx.serialization.Serializable

@Serializable
data class EmpresaDto(
    val id: Int,
    val nombre: String,
    val tasaInteres: Double
)
