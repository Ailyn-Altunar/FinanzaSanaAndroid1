package com.ailyn.finanzasana.features.home.deuda.domain.usecases

import com.ailyn.finanzasana.features.home.deuda.domain.entities.Abono
import com.ailyn.finanzasana.features.home.deuda.domain.repositories.DeudaRepository
import javax.inject.Inject

class GetAbonosUseCase @Inject constructor(
    private val repository: DeudaRepository
) {
    suspend operator fun invoke(idDeuda: Int): List<Abono> =
        repository.getAbonos(idDeuda)
}
