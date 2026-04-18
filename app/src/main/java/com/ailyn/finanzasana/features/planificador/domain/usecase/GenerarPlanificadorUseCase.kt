package com.ailyn.finanzasana.features.planificador.domain.usecase

import com.ailyn.finanzasana.features.planificador.domain.model.PlanificadorResultado
import com.ailyn.finanzasana.features.planificador.domain.repository.PlanificadorRepository
import javax.inject.Inject


class GenerarPlanificadorUseCase @Inject constructor(
    private val repository: PlanificadorRepository
) {

    suspend operator fun invoke(metodo: String): Result<PlanificadorResultado> {
        return repository.obtenerPlan(metodo)
    }
}