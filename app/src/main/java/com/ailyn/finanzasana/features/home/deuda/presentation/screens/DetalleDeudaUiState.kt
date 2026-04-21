package com.ailyn.finanzasana.features.home.deuda.presentation.screens

import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda

data class DetalleDeudaUiState(
    val isLoading: Boolean = false,
    val deuda: Deuda? = null,
    val errorMessage: String? = null,
    val isAbonoSuccess: Boolean = false
)
