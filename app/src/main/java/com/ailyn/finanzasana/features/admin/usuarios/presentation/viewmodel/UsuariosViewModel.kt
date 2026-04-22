package com.ailyn.finanzasana.features.admin.usuarios.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.admin.usuarios.domain.entities.UsuarioAdmin
import com.ailyn.finanzasana.features.admin.usuarios.domain.usecases.EliminarUsuarioUseCase
import com.ailyn.finanzasana.features.admin.usuarios.domain.usecases.GetUsuariosUseCase
import com.ailyn.finanzasana.features.admin.usuarios.presentation.screens.UsuariosUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsuariosUiState(
    val isLoading: Boolean = false,
    val usuarios: List<UsuarioAdmin> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)

@HiltViewModel
class UsuariosViewModel @Inject constructor(
    private val getUsuariosUseCase: GetUsuariosUseCase,
    private val eliminarUsuarioUseCase: EliminarUsuarioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsuariosUiState())
    val uiState: StateFlow<UsuariosUiState> = _uiState.asStateFlow()

    init {
        cargarUsuarios()
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val usuarios = getUsuariosUseCase()
                _uiState.update { it.copy(isLoading = false, usuarios = usuarios) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun eliminarUsuario(id: Int) {
        viewModelScope.launch {
            try {
                eliminarUsuarioUseCase(id)
                cargarUsuarios()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error al eliminar usuario") }
            }
        }
    }
}
