package com.ailyn.finanzasana.features.auth.login.presentation.screens

data class RegisterUiState(
    val nombre: String = "",
    val email: String = "",
    val telefono: String = "",
    val contrasena: String = "",

    val errorNombre: String? = null,
    val errorEmail: String? = null,
    val errorTelefono: String? = null,
    val errorContrasena: String? = null,

    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false,
    val errorMessage: String? = null
)
