package com.ailyn.finanzasana.features.home.deuda.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.home.deuda.domain.usecases.GetDeudasUseCase
import com.ailyn.finanzasana.features.home.deuda.domain.usecases.GetResumenDeudaUseCase
import com.ailyn.finanzasana.features.home.deuda.presentation.screens.DeudaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeudaViewModel @Inject constructor(
    private val getDeudasUseCase: GetDeudasUseCase,
    private val getResumenDeudaUseCase: GetResumenDeudaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeudaUiState())
    val uiState: StateFlow<DeudaUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val deudas = getDeudasUseCase()
                val resumen = getResumenDeudaUseCase()
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        deudas = deudas,
                        totalAdeudado = resumen.totalAdeudado,
                        cantidadDeudasActivas = resumen.cantidadDeudasActivas
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = "Error al cargar finanzas: ${e.message}" 
                    )
                }
            }
        }
    }
}
