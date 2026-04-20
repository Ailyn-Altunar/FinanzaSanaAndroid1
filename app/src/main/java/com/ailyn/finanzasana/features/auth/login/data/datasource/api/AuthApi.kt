package com.ailyn.finanzasana.features.auth.login.data.datasource.api

import com.ailyn.finanzasana.features.auth.login.data.models.LoginRequest
import com.ailyn.finanzasana.features.auth.login.data.models.LoginResponse
import com.ailyn.finanzasana.features.auth.login.data.models.UsuarioRequest
import com.ailyn.finanzasana.features.auth.login.data.models.UsuarioResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("usuarios/registro")
    suspend fun register(
        @Body request: UsuarioRequest
    ): UsuarioResponse

    @GET("usuarios/perfil")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): UsuarioResponse
}
