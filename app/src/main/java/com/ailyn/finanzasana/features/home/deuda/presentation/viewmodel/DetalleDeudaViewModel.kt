package com.ailyn.finanzasana.features.home.deuda.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda
import com.ailyn.finanzasana.features.home.deuda.domain.usecases.GetAbonosUseCase
import com.ailyn.finanzasana.features.home.deuda.domain.usecases.LiquidarDeudaUseCase
import com.ailyn.finanzasana.features.home.deuda.domain.usecases.RegistrarAbonoUseCase
import com.ailyn.finanzasana.features.home.deuda.presentation.screens.DetalleDeudaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleDeudaViewModel @Inject constructor(
    private val registrarAbonoUseCase: RegistrarAbonoUseCase,
    private val getAbonosUseCase: GetAbonosUseCase,
    private val liquidarDeudaUseCase: LiquidarDeudaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleDeudaUiState())
    val uiState: StateFlow<DetalleDeudaUiState> = _uiState.asStateFlow()

    fun setDeuda(deuda: Deuda) {
        _uiState.update { it.copy(deuda = deuda) }
        deuda.id?.let { cargarAbonos(it) }
    }

    private fun cargarAbonos(idDeuda: Int) {
        viewModelScope.launch {
            try {
                val abonos = getAbonosUseCase(idDeuda)
                _uiState.update { state ->
                    val d = state.deuda ?: return@update state
                    state.copy(deuda = d.copy(abonos = abonos))
                }
            } catch (e: Exception) {
                // Error silencioso al cargar historial
            }
        }
    }

    fun registrarAbono(monto: Double) {
        val currentDeuda = _uiState.value.deuda ?: return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val nuevoAbono = registrarAbonoUseCase(currentDeuda.id ?: 0, monto)
                
                _uiState.update { state ->
                    val d = state.deuda ?: return@update state
                    val nuevaListaAbonos = d.abonos + nuevoAbono
                    val nuevoSaldo = d.saldoActual - monto
                    val nuevoPorcentaje = if (d.montoOriginal > 0) {
                        ((d.montoOriginal - nuevoSaldo) / d.montoOriginal) * 100
                    } else 0.0

                    state.copy(
                        deuda = d.copy(
                            saldoActual = nuevoSaldo,
                            abonos = nuevaListaAbonos,
                            porcentajePagado = nuevoPorcentaje
                        ),
                        isLoading = false,
                        isAbonoSuccess = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun liquidarDeuda() {
        val currentDeuda = _uiState.value.deuda ?: return
        val montoALiquidar = currentDeuda.saldoActual
        
        viewModelScope.launch {
            // 1. ACTUALIZACIÓN LOCAL INMEDIATA (Optimista)
            _uiState.update { state ->
                val d = state.deuda ?: return@update state
                val abonoLiquidacion = com.ailyn.finanzasana.features.home.deuda.domain.entities.Abono(
                    id = -1,
                    monto = montoALiquidar,
                    fecha = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                )
                state.copy(
                    deuda = d.copy(
                        saldoActual = 0.0,
                        porcentajePagado = 100.0,
                        abonos = d.abonos + abonoLiquidacion
                    ),
                    isAbonoSuccess = true
                )
            }

            try {
                // 2. LLAMADA A LA API EN SEGUNDO PLANO
                liquidarDeudaUseCase(currentDeuda.id ?: 0)
                
                // 3. REFRESCAR DATOS REALES
                currentDeuda.id?.let { cargarAbonos(it) }
            } catch (e: Exception) {
                // Si falla, revertimos o informamos
                _uiState.update { it.copy(errorMessage = "Error al liquidar en servidor: ${e.message}") }
            }
        }
    }
}
