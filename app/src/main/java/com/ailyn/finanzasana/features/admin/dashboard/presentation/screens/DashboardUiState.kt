package com.ailyn.finanzasana.features.admin.dashboard.presentation.screens

import com.ailyn.finanzasana.features.admin.dashboard.domain.entities.DashboardData

data class DashboardUiState(
    val isLoading: Boolean = false,
    val data: DashboardData? = null,
    val errorMessage: String? = null
)
