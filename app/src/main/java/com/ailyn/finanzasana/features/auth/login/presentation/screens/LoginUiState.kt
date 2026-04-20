package com.ailyn.finanzasana.features.auth.login.presentation.screens

data class LoginUiState(

    // Campos del formulario
    val email: String = "",
    val contrasena: String = "",

    // Errores por campo
    val errorEmail: String? = null,
    val errorContrasena: String? = null,

    // Estados de proceso
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,

    // Resultado del login
    val rol: Int? = null,

    // Error general
    val errorMessage: String? = null
)
