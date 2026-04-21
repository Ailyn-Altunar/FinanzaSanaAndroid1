package com.ailyn.finanzasana.features.admin.usuarios.domain.entities

data class UsuarioAdmin(
    val id: Int,
    val nombre: String,
    val email: String,
    val totalDeudas: Int
)
