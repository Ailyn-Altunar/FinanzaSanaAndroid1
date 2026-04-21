package com.ailyn.finanzasana.features.home.categoria.domain.repositories

import com.ailyn.finanzasana.features.home.categoria.domain.entities.Categoria

interface CategoriasRepository {
    suspend fun getCategorias(): List<Categoria>
}
