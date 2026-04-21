package com.ailyn.finanzasana.features.home.empresas.domain.repositories
import com.ailyn.finanzasana.features.home.empresas.domain.entities.Empresa

interface EmpresasRepository {
    suspend fun getEmpresas(): List<Empresa>
}
