package com.ailyn.finanzasana.features.home.empresas.data.repositories

import com.ailyn.finanzasana.features.home.empresas.domain.entities.Empresa
import com.ailyn.finanzasana.features.home.empresas.data.datasource.api.EmpresasApi
import com.ailyn.finanzasana.features.home.empresas.data.mapper.toDomain
import com.ailyn.finanzasana.features.home.empresas.domain.repositories.EmpresasRepository
import javax.inject.Inject

class EmpresasRepositoryImpl @Inject constructor(
    private val api: EmpresasApi
) : EmpresasRepository {

    override suspend fun getEmpresas(): List<Empresa> {
        return api.getEmpresas().map { it.toDomain() }
    }
}
