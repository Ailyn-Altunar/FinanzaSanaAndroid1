package com.ailyn.finanzasana.features.home.deuda.domain.usecases

import com.ailyn.finanzasana.features.home.deuda.domain.entities.ResumenDeuda
import com.ailyn.finanzasana.features.home.deuda.domain.repositories.DeudaRepository
import javax.inject.Inject

class GetResumenDeudaUseCase @Inject constructor(
    private val repository: DeudaRepository
) {
    suspend operator fun invoke(): ResumenDeuda = repository.getResumenDeuda()
}
