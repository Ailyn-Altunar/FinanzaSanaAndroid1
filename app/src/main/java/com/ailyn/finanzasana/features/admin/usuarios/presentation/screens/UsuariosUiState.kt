package com.ailyn.finanzasana.features.admin.usuarios.presentation.screens

import com.ailyn.finanzasana.features.admin.usuarios.domain.entities.UsuarioAdmin

data class UsuariosUiState(
    val isLoading: Boolean = false,
    val usuarios: List<UsuarioAdmin> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)
