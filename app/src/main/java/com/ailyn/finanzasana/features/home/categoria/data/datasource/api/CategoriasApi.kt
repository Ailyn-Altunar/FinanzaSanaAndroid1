package com.ailyn.finanzasana.features.home.categoria.data.datasource.api

import com.ailyn.finanzasana.features.home.categoria.data.models.CategoriaDto
import retrofit2.http.GET

interface CategoriasApi {

    @GET("/categorias")
    suspend fun getCategorias(): List<CategoriaDto>
}
