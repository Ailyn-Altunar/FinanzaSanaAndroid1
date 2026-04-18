package com.ailyn.finanzasana.features.admin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.admin.data.mapper.toDomain
import com.ailyn.finanzasana.features.admin.data.mapper.toDomainList
import com.ailyn.finanzasana.features.admin.domain.model.ActividadAdmin
import com.ailyn.finanzasana.features.admin.domain.model.AdminMetrics
import com.ailyn.finanzasana.features.admin.domain.usecase.GetAdminActivityUseCase
import com.ailyn.finanzasana.features.admin.domain.usecase.GetAdminMetricsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AdminUiState {
    object Loading : AdminUiState
    data class Success(
        val metrics: AdminMetrics,
        val activities: List<ActividadAdmin>
    ) : AdminUiState
    data class Error(val message: String) : AdminUiState
}

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val getMetricsUseCase: GetAdminMetricsUseCase,
    private val getActivityUseCase: GetAdminActivityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AdminUiState>(AdminUiState.Loading)
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = AdminUiState.Loading

            val metricsDeferred = async { getMetricsUseCase() }
            val activityDeferred = async { getActivityUseCase() }

            val metricsResult = metricsDeferred.await()
            val activityResult = activityDeferred.await()

            if (metricsResult.isSuccess && activityResult.isSuccess) {
                val metrics = metricsResult.getOrNull()?.toDomain()
                val activities = activityResult.getOrNull()?.toDomainList()

                if (metrics != null && activities != null) {
                    _uiState.value = AdminUiState.Success(metrics, activities)
                } else {
                    _uiState.value = AdminUiState.Error("Error en la transformación de datos")
                }
            } else {
                val errorMsg = metricsResult.exceptionOrNull()?.message
                    ?: activityResult.exceptionOrNull()?.message
                    ?: "Error de conexión con el servidor"
                _uiState.value = AdminUiState.Error(errorMsg)
            }
        }
    }
}
