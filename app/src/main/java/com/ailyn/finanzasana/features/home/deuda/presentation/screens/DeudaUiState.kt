package com.ailyn.finanzasana.features.home.deuda.presentation.screens

import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda

data class DeudaUiState(
    val isLoading: Boolean = false,
    val deudas: List<Deuda> = emptyList(),
    val totalAdeudado: Double = 0.0,
    val cantidadDeudasActivas: Int = 0,
    val errorMessage: String? = null
)
