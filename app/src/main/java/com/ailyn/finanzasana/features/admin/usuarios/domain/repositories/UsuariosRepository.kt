package com.ailyn.finanzasana.features.admin.usuarios.domain.repositories

import com.ailyn.finanzasana.features.admin.usuarios.domain.entities.UsuarioAdmin

interface UsuariosRepository {
    suspend fun getUsuarios(): List<UsuarioAdmin>
    suspend fun eliminarUsuario(id: Int)
}
