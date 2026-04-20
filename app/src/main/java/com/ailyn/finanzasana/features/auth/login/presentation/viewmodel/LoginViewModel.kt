package com.ailyn.finanzasana.features.auth.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.auth.login.domain.usecases.LoginUseCase
import com.ailyn.finanzasana.features.auth.login.presentation.screens.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // -----------------------------
    //  CAMBIOS DE CAMPOS
    // -----------------------------
    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, errorEmail = null, errorMessage = null) }
    }

    fun onContrasenaChange(value: String) {
        _uiState.update { it.copy(contrasena = value, errorContrasena = null, errorMessage = null) }
    }

    // -----------------------------
    //  LOGIN
    // -----------------------------
    fun login() {
        val state = _uiState.value
        var isValid = true

        // Validación por campo
        if (state.email.isBlank()) {
            _uiState.update { it.copy(errorEmail = "Obligatorio") }
            isValid = false
        }

        if (state.contrasena.isBlank()) {
            _uiState.update { it.copy(errorContrasena = "Obligatorio") }
            isValid = false
        }

        if (!isValid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = loginUseCase(state.email, state.contrasena)

            if (result != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = true,
                        rol = result.usuario.idRol
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Credenciales incorrectas"
                    )
                }
            }
        }
    }

    // -----------------------------
    //  LIMPIEZA DE ESTADOS
    // -----------------------------
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun resetLoginSuccess() {
        _uiState.update { it.copy(loginSuccess = false) }
    }
}
