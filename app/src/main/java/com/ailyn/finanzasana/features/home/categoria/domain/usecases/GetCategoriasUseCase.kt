package com.ailyn.finanzasana.features.home.categoria.domain.usecases

import com.ailyn.finanzasana.features.home.categoria.domain.repositories.CategoriasRepository
import javax.inject.Inject

class GetCategoriasUseCase @Inject constructor(
    private val repository: CategoriasRepository
) {
    suspend operator fun invoke() = repository.getCategorias()
}
