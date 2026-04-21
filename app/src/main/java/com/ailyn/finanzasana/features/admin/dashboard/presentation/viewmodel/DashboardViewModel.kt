package com.ailyn.finanzasana.features.admin.dashboard.presentation.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.core.hardware.biometric.domain.BiometricAuthenticator
import com.ailyn.finanzasana.features.admin.dashboard.domain.usecases.GetDashboardUseCase
import com.ailyn.finanzasana.features.admin.dashboard.presentation.screens.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardUseCase: GetDashboardUseCase,
    private val biometricAuthenticator: BiometricAuthenticator
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val data = getDashboardUseCase()
                _uiState.update { it.copy(isLoading = false, data = data) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun autenticarYAcceder(
        activity: FragmentActivity,
        onSuccess: () -> Unit
    ) {
        biometricAuthenticator.authenticate(
            activity = activity,
            title = "Acceso Requerido",
            subtitle = "Confirma tu identidad para ver el directorio de usuarios",
            onSuccess = onSuccess,
            onError = { error ->
                _uiState.update { it.copy(errorMessage = error) }
            }
        )
    }
}
