package com.ailyn.finanzasana.features.home.domain.usecase

import com.ailyn.finanzasana.features.home.domain.model.Abono
import com.ailyn.finanzasana.features.home.domain.repository.DeudaRepository
import javax.inject.Inject


class RegistrarAbonoUseCase @Inject constructor(
    private val repository: DeudaRepository
) {

    suspend operator fun invoke(idDeuda: Int, monto: Double): Result<Abono> {
        return repository.registrarAbono(idDeuda, monto)
    }
}