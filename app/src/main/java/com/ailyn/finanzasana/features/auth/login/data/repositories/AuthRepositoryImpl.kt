package com.ailyn.finanzasana.features.auth.login.data.repositories

import com.ailyn.finanzasana.features.auth.login.data.datasource.api.AuthApi
import com.ailyn.finanzasana.features.auth.login.data.mapper.toDomain
import com.ailyn.finanzasana.features.auth.login.data.models.LoginRequest
import com.ailyn.finanzasana.features.auth.login.data.models.UsuarioRequest
import com.ailyn.finanzasana.features.auth.login.domain.entities.Login
import com.ailyn.finanzasana.features.auth.login.domain.entities.Usuario
import com.ailyn.finanzasana.features.auth.login.domain.repositories.AuthRepository
import com.ailyn.finanzasana.core.session.SessionManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, contrasena: String): Login? {
        return try {
            val login = api.login(LoginRequest(email, contrasena)).toDomain()

            // Guardar sesión
            saveSession(login)

            login
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun register(
        nombre: String,
        email: String,
        contrasena: String,
        idRol: Int,
        telefono: String
    ): Usuario? {
        return try {
            api.register(
                UsuarioRequest(
                    nombre = nombre,
                    email = email,
                    contrasena = contrasena,
                    idRol = idRol,
                    telefono = telefono
                )
            ).toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getProfile(): Usuario? {
        return try {
            val token = sessionManager.getToken() ?: return null
            api.getProfile("Bearer $token").toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveSession(session: Login) {
        sessionManager.saveSession(
            token = session.token,
            rol = session.usuario.idRol,
            idUsuario = session.usuario.id
        )
    }

    override suspend fun getSession(): Login? {
        val token = sessionManager.getToken() ?: return null
        val id = sessionManager.getUserId()
        val rol = sessionManager.getRol()

        if (id == -1 || rol == -1) return null

        return Login(
            token = token,
            usuario = Usuario(
                id = id,
                nombre = "",
                email = "",
                idRol = rol,
                telefono = ""
            )
        )
    }

    override suspend fun clearSession() {
        sessionManager.clearSession()
    }
}
