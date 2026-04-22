package com.ailyn.finanzasana.features.auth.login.presentation.screens

data class LoginUiState(

    val email: String = "",
    val contrasena: String = "",

    val errorEmail: String? = null,
    val errorContrasena: String? = null,

    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,

    val rol: Int? = null,

    val errorMessage: String? = null
)
