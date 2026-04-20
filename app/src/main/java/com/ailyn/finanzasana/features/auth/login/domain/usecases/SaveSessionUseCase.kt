package com.ailyn.finanzasana.features.auth.login.domain.usecases

import com.ailyn.finanzasana.features.auth.login.domain.entities.Login
import com.ailyn.finanzasana.features.auth.login.domain.repositories.AuthRepository

class SaveSessionUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(session: Login) =
        repository.saveSession(session)
}
