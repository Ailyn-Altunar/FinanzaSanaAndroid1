package com.ailyn.finanzasana.features.home.data.datasource

import com.ailyn.finanzasana.features.home.data.model.CategoriaResponse


interface CategoriaDataSource {
    suspend fun getCategorias(): List<CategoriaResponse>
}
