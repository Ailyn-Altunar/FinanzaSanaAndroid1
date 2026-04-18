package com.ailyn.finanzasana.features.home.domain.usecase

import com.ailyn.finanzasana.features.home.domain.model.Deuda
import com.ailyn.finanzasana.features.home.domain.repository.DeudaRepository
import javax.inject.Inject


class GetDeudasUseCase @Inject constructor(
    private val repository: DeudaRepository
) {

    suspend operator fun invoke(): Result<List<Deuda>> {
        return repository.getDeudas()
    }
}