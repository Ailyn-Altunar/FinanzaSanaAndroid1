package com.ailyn.finanzasana.features.home.categoria.data.repositories

import com.ailyn.finanzasana.features.home.categoria.data.datasource.api.CategoriasApi
import com.ailyn.finanzasana.features.home.categoria.domain.repositories.CategoriasRepository
import com.ailyn.finanzasana.features.home.categoria.data.mapper.toDomain
import com.ailyn.finanzasana.features.home.categoria.domain.entities.Categoria
import javax.inject.Inject

class CategoriasRepositoryImpl @Inject constructor(
    private val api: CategoriasApi
) : CategoriasRepository {

    override suspend fun getCategorias(): List<Categoria> {
        return api.getCategorias().map { it.toDomain() }
    }
}
