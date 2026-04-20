package com.ailyn.finanzasana.features.auth.login.domain.usecases

import com.ailyn.finanzasana.features.auth.login.domain.entities.Usuario
import com.ailyn.finanzasana.features.auth.login.domain.repositories.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        nombre: String,
        email: String,
        contrasena: String,
        idRol: Int,
        telefono: String
    ): Usuario? = repository.register(
        nombre = nombre,
        email = email,
        contrasena = contrasena,
        idRol = idRol,
        telefono = telefono
    )
}
