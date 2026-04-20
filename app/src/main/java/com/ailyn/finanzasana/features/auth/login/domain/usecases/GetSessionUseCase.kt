package com.ailyn.finanzasana.features.auth.login.domain.usecases

import com.ailyn.finanzasana.features.auth.login.domain.entities.Login
import com.ailyn.finanzasana.features.auth.login.domain.repositories.AuthRepository

class GetSessionUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Login? =
        repository.getSession()
}
