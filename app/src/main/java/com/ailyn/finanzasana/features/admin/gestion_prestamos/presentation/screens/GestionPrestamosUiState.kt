package com.ailyn.finanzasana.features.admin.gestion_prestamos.presentation.screens

import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin

data class GestionPrestamosUiState(
    val isLoading: Boolean = false,
    val pendientes: List<SolicitudAdmin> = emptyList(),
    val historial: List<SolicitudAdmin> = emptyList(),
    val error: String? = null
)
