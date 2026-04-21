package com.ailyn.finanzasana.features.admin.gestion_prestamos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.usecases.AprobarSolicitudUseCase
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.usecases.GetSolicitudesAdminUseCase
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.usecases.RechazarSolicitudUseCase
import com.ailyn.finanzasana.features.admin.gestion_prestamos.presentation.screens.GestionPrestamosUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GestionPrestamosViewModel @Inject constructor(
    private val getSolicitudesAdminUseCase: GetSolicitudesAdminUseCase,
    private val aprobarSolicitudUseCase: AprobarSolicitudUseCase,
    private val rechazarSolicitudUseCase: RechazarSolicitudUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GestionPrestamosUiState())
    val uiState: StateFlow<GestionPrestamosUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val pendientes = getSolicitudesAdminUseCase.getPendientes()
                val historial = getSolicitudesAdminUseCase.getHistorial()
                _uiState.update { 
                    it.copy(isLoading = false, pendientes = pendientes, historial = historial) 
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun aprobarSolicitud(id: Int) {
        viewModelScope.launch {
            try {
                aprobarSolicitudUseCase(id)
                cargarDatos()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun rechazarSolicitud(id: Int) {
        viewModelScope.launch {
            try {
                rechazarSolicitudUseCase(id)
                cargarDatos()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
