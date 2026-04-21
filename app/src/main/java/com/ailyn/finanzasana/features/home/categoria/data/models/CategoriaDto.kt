package com.ailyn.finanzasana.features.home.categoria.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoriaDto(
    val id: Int,
    val nombre: String
)
