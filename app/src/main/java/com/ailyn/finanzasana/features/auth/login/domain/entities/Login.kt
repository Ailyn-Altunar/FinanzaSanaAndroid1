package com.ailyn.finanzasana.features.auth.login.domain.entities

data class Login(
    val usuario: Usuario,
    val token: String
)
