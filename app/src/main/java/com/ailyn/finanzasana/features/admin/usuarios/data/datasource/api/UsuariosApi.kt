package com.ailyn.finanzasana.features.admin.usuarios.data.datasource.api

import com.ailyn.finanzasana.features.admin.usuarios.data.models.EliminarUsuarioResponse
import com.ailyn.finanzasana.features.admin.usuarios.data.models.UsuarioAdminDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UsuariosApi {
    @GET("admin/usuarios")
    suspend fun getUsuarios(
        @Header("Authorization") token: String
    ): List<UsuarioAdminDto>

    @DELETE("usuarios/{id}")
    suspend fun eliminarUsuario(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): EliminarUsuarioResponse
}
