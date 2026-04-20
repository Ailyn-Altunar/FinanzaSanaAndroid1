package com.ailyn.finanzasana.features.auth.login.domain.entities

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val idRol: Int,
    val telefono: String
)
