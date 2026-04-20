package com.ailyn.finanzasana.features.auth.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.auth.login.presentation.screens.RegisterUiState
import com.ailyn.finanzasana.features.auth.login.domain.usecases.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNombreChange(value: String) {
        _uiState.update { it.copy(nombre = value, errorNombre = null, errorMessage = null) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, errorEmail = null, errorMessage = null) }
    }

    fun onTelefonoChange(value: String) {
        _uiState.update { it.copy(telefono = value, errorTelefono = null, errorMessage = null) }
    }

    fun onContrasenaChange(value: String) {
        _uiState.update { it.copy(contrasena = value, errorContrasena = null, errorMessage = null) }
    }

    fun registrar() {
        val state = _uiState.value
        var valid = true

        if (state.nombre.isBlank()) {
            _uiState.update { it.copy(errorNombre = "Obligatorio") }
            valid = false
        }
        if (state.email.isBlank()) {
            _uiState.update { it.copy(errorEmail = "Obligatorio") }
            valid = false
        }
        if (state.telefono.isBlank()) {
            _uiState.update { it.copy(errorTelefono = "Obligatorio") }
            valid = false
        }
        if (state.contrasena.isBlank()) {
            _uiState.update { it.copy(errorContrasena = "Obligatorio") }
            valid = false
        }

        if (!valid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = registerUseCase(
                nombre = state.nombre,
                email = state.email,
                contrasena = state.contrasena,
                idRol = 2,
                telefono = state.telefono
            )


            if (result != null) {
                _uiState.update { it.copy(isLoading = false, registerSuccess = true) }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Error al registrar") }
            }
        }
    }

    fun resetSuccess() {
        _uiState.update { it.copy(registerSuccess = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
