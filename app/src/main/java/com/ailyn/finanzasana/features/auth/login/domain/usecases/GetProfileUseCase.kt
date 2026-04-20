package com.ailyn.finanzasana.features.auth.login.domain.usecases

import com.ailyn.finanzasana.features.auth.login.domain.entities.Usuario
import com.ailyn.finanzasana.features.auth.login.domain.repositories.AuthRepository

class GetProfileUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Usuario? =
        repository.getProfile()
}
