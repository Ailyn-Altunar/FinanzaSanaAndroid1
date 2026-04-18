package com.ailyn.finanzasana.features.admin.domain.model

data class UserAdmin(
    val id: Int,
    val nombre: String,
    val email: String,
    val totalDeudas: Int
)