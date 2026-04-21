package com.ailyn.finanzasana.features.admin.usuarios.data.repositories

import com.ailyn.finanzasana.core.session.SessionManager
import com.ailyn.finanzasana.features.admin.usuarios.data.datasource.api.UsuariosApi
import com.ailyn.finanzasana.features.admin.usuarios.data.mapper.toDomain
import com.ailyn.finanzasana.features.admin.usuarios.domain.entities.UsuarioAdmin
import com.ailyn.finanzasana.features.admin.usuarios.domain.repositories.UsuariosRepository
import javax.inject.Inject

class UsuariosRepositoryImpl @Inject constructor(
    private val api: UsuariosApi,
    private val sessionManager: SessionManager
) : UsuariosRepository {
    
    private fun getToken() = "Bearer ${sessionManager.getToken() ?: ""}"

    override suspend fun getUsuarios(): List<UsuarioAdmin> {
        return api.getUsuarios(getToken()).map { it.toDomain() }
    }

    override suspend fun eliminarUsuario(id: Int) {
        api.eliminarUsuario(getToken(), id)
    }
}
