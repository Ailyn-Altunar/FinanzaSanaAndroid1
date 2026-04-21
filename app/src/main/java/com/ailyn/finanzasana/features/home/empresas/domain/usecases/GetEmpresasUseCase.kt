package com.ailyn.finanzasana.features.home.empresas.domain.usecases


import com.ailyn.finanzasana.features.home.empresas.domain.repositories.EmpresasRepository
import javax.inject.Inject

class GetEmpresasUseCase @Inject constructor(
    private val repository: EmpresasRepository
) {
    suspend operator fun invoke() = repository.getEmpresas()
}
