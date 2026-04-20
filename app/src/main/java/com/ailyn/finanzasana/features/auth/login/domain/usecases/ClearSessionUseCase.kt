package com.ailyn.finanzasana.features.auth.login.domain.usecases

import com.ailyn.finanzasana.features.auth.login.domain.repositories.AuthRepository

class ClearSessionUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() =
        repository.clearSession()
}
