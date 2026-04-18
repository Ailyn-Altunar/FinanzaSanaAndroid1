package com.ailyn.finanzasana.features.admin.presentation.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.admin.data.mapper.toDomainListUsers
import com.ailyn.finanzasana.features.admin.domain.model.UserAdmin
import com.ailyn.finanzasana.features.admin.domain.usecase.GetAdminUsersUseCase
import com.ailyn.finanzasana.features.admin.domain.usecase.DeleteAdminUserUseCase
import com.ailyn.finanzasana.features.admin.domain.usecase.AutenticarHuellaUseCase
import com.ailyn.finanzasana.features.admin.presentation.screens.UserManagementUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserManagementViewModel @Inject constructor(
    private val getAdminUsersUseCase: GetAdminUsersUseCase,
    private val deleteAdminUserUseCase: DeleteAdminUserUseCase,
    private val autenticarHuellaUseCase: AutenticarHuellaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserManagementUiState())
    val uiState: StateFlow<UserManagementUiState> = _uiState

    private var usuariosCompletos: List<UserAdmin> = emptyList()

    init {
        loadUsers()
    }

    fun autenticar(activity: FragmentActivity) {
        viewModelScope.launch {
            val ok = autenticarHuellaUseCase(activity)
            _uiState.update { it.copy(isAuthenticated = ok) }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            getAdminUsersUseCase().onSuccess { dtos ->
                usuariosCompletos = dtos.toDomainListUsers()
                _uiState.update {
                    it.copy(
                        users = usuariosCompletos,
                        filteredUsers = usuariosCompletos,
                        isLoading = false
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "No se pudo cargar la lista de usuarios"
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        val filtrados = if (query.isBlank()) {
            usuariosCompletos
        } else {
            usuariosCompletos.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.email.contains(query, ignoreCase = true)
            }
        }

        _uiState.update { it.copy(filteredUsers = filtrados) }
    }

    fun deleteUser(idUsuario: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true, deleteError = null, deleteSuccess = null) }

            deleteAdminUserUseCase(idUsuario).onSuccess {
                usuariosCompletos = usuariosCompletos.filter { it.id != idUsuario }
                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        deleteSuccess = true,
                        users = usuariosCompletos,
                        filteredUsers = usuariosCompletos
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        deleteError = exception.message ?: "No se pudo eliminar al usuario"
                    )
                }
            }
        }
    }

    fun clearError() = _uiState.update { it.copy(errorMessage = null) }
    fun clearDeleteSuccess() = _uiState.update { it.copy(deleteSuccess = null) }
    fun clearUiMessage() = _uiState.update { it.copy(uiMessage = null) }
}
