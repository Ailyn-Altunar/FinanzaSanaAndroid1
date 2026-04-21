package com.ailyn.finanzasana.features.home.deuda.domain.usecases

import com.ailyn.finanzasana.features.home.deuda.domain.entities.Abono
import com.ailyn.finanzasana.features.home.deuda.domain.repositories.DeudaRepository
import javax.inject.Inject

class RegistrarAbonoUseCase @Inject constructor(
    private val repository: DeudaRepository
) {
    suspend operator fun invoke(idDeuda: Int, monto: Double): Abono =
        repository.registrarAbono(idDeuda, monto)
}
