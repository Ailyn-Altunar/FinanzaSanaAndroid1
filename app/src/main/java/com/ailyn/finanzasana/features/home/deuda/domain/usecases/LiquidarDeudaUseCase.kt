package com.ailyn.finanzasana.features.home.deuda.domain.usecases

import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda
import com.ailyn.finanzasana.features.home.deuda.domain.repositories.DeudaRepository
import javax.inject.Inject

class LiquidarDeudaUseCase @Inject constructor(
    private val repository: DeudaRepository
) {
    suspend operator fun invoke(idDeuda: Int): Deuda =
        repository.liquidarDeuda(idDeuda)
}
