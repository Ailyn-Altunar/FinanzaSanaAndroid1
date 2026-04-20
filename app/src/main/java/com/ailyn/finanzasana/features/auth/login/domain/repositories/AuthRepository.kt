package com.ailyn.finanzasana.features.auth.login.domain.repositories

import com.ailyn.finanzasana.features.auth.login.domain.entities.Login
import com.ailyn.finanzasana.features.auth.login.domain.entities.Usuario

interface AuthRepository {

    suspend fun login(email: String, contrasena: String): Login?

    suspend fun register(
        nombre: String,
        email: String,
        contrasena: String,
        idRol: Int,
        telefono: String
    ): Usuario?

    suspend fun getProfile(): Usuario?

    suspend fun saveSession(session: Login)

    suspend fun getSession(): Login?

    suspend fun clearSession()
}
