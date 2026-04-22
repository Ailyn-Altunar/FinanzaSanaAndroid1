package com.ailyn.finanzasana.features.home.solicitudes.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.core.hardware.camera.domain.CameraManager
import com.ailyn.finanzasana.core.hardware.location.domain.LocationManager
import com.ailyn.finanzasana.core.network.NetworkManager
import com.ailyn.finanzasana.features.home.categoria.domain.entities.Categoria
import com.ailyn.finanzasana.features.home.empresas.domain.entities.Empresa
import com.ailyn.finanzasana.features.home.solicitudes.domain.entities.NuevaSolicitud
import com.ailyn.finanzasana.features.home.solicitudes.domain.usecases.CrearSolicitudUseCase
import com.ailyn.finanzasana.features.home.solicitudes.presentation.screens.SolicitudPrestamoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolicitudPrestamoViewModel @Inject constructor(
    private val crearSolicitudUseCase: CrearSolicitudUseCase,
    private val networkManager: NetworkManager,
    private val cameraManager: CameraManager,
    private val locationManager: LocationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SolicitudPrestamoUiState())
    val uiState: StateFlow<SolicitudPrestamoUiState> = _uiState.asStateFlow()

    private var empresasCache: List<Empresa> = emptyList()
    private var categoriasCache: List<Categoria> = emptyList()


    fun setEmpresas(empresas: List<Empresa>) {
        empresasCache = empresas
        _uiState.value.empresaId?.let { id ->
            onEmpresaSeleccionada(id)
        }
    }

    fun setCategorias(categorias: List<Categoria>) {
        categoriasCache = categorias
    }


    fun onEmpresaSeleccionada(id: Int) {
        val empresa = empresasCache.find { it.id == id } ?: return
        _uiState.update {
            it.copy(
                empresaId = id,
                tasaInteres = empresa.tasaInteres,
                errorEmpresa = null
            )
        }
    }


    fun onCategoriaSeleccionada(id: Int) {
        _uiState.update {
            it.copy(
                categoriaId = id,
                errorCategoria = null
            )
        }
    }


    fun intentarTomarFoto(
        onAbrirCamara: () -> Unit,
        onPedirPermiso: () -> Unit
    ) {
        if (cameraManager.tienePermisoCamara()) {
            onAbrirCamara()
        } else {
            onPedirPermiso()
        }
    }

    fun onImagenSeleccionada(bitmap: Bitmap) {
        val fotoModel = cameraManager.bitmapToBase64(bitmap)
        _uiState.update { it.copy(imagenBase64 = fotoModel.base64, errorMessage = null) }
    }

    fun onImagenEliminada() {
        _uiState.update { it.copy(imagenBase64 = null) }
    }

    fun onPermissionDenied(permission: String) {
        _uiState.update { it.copy(errorMessage = "Permiso de $permission denegado") }
    }


    fun obtenerUbicacion() {
        viewModelScope.launch {
            val ub = locationManager.obtenerUbicacion()
            if (ub != null) {
                _uiState.update {
                    it.copy(
                        latitud = ub.latitud,
                        longitud = ub.longitud,
                        direccion = ub.direccion ?: "${ub.latitud}, ${ub.longitud}",
                        errorUbicacion = null
                    )
                }
            } else {
                _uiState.update { it.copy(errorUbicacion = "Error al obtener GPS") }
            }
        }
    }


    fun onMontoChange(value: String) =
        _uiState.update { it.copy(montoSolicitado = value, errorMonto = null) }

    fun onMesesChange(value: String) =
        _uiState.update { it.copy(meses = value, errorMeses = null) }

    fun onMotivoChange(value: String) =
        _uiState.update { it.copy(motivo = value, errorMotivo = null) }


    private fun validar(): Boolean {
        val s = _uiState.value
        var ok = true

        if (s.empresaId == null) {
            _uiState.update { it.copy(errorEmpresa = "Seleccione una empresa") }
            ok = false
        }

        if (s.categoriaId == null) {
            _uiState.update { it.copy(errorCategoria = "Seleccione una categoría") }
            ok = false
        }

        if (s.montoSolicitado.isBlank()) {
            _uiState.update { it.copy(errorMonto = "Obligatorio") }
            ok = false
        }

        if (s.meses.isBlank()) {
            _uiState.update { it.copy(errorMeses = "Obligatorio") }
            ok = false
        }

        if (s.motivo.isBlank()) {
            _uiState.update { it.copy(errorMotivo = "Describa el motivo") }
            ok = false
        }

        return ok
    }


    fun enviarSolicitud(idUsuario: Int) {
        if (!validar()) return

        if (!networkManager.isNetworkAvailable()) {
            _uiState.update { it.copy(errorMessage = "Sin conexión a internet") }
            return
        }

        val s = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val monto = s.montoSolicitado.toDoubleOrNull() ?: 0.0
            val mesesInt = s.meses.toIntOrNull() ?: 0

            val solicitud = NuevaSolicitud(
                idUsuario = idUsuario,
                idEmpresa = s.empresaId!!,
                montoSolicitado = monto,
                meses = mesesInt,
                motivo = s.motivo,
                tasaInteres = s.tasaInteres!!,
                idCategoria = s.categoriaId!!,
                imagenBase64 = s.imagenBase64,
                latitud = s.latitud,
                longitud = s.longitud
            )

            try {
                crearSolicitudUseCase(solicitud)
                _uiState.update { it.copy(isLoading = false, success = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun resetSuccess() = _uiState.update { it.copy(success = false) }
    fun clearError() = _uiState.update { it.copy(errorMessage = null) }
}
