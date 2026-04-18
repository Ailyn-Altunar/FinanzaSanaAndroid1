package com.ailyn.finanzasana.features.home.domain.usecase

import com.ailyn.finanzasana.features.home.data.model.DeudaRequest
import com.ailyn.finanzasana.features.home.domain.model.Deuda
import com.ailyn.finanzasana.features.home.domain.repository.DeudaRepository
import javax.inject.Inject


class RegistrarDeudaUseCase @Inject constructor(
    private val repository: DeudaRepository
) {

    suspend operator fun invoke(request: DeudaRequest): Result<Deuda> {
        return repository.registrarDeuda(request)
    }
}