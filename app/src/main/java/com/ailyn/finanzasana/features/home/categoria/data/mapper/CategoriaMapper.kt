package com.ailyn.finanzasana.features.home.categoria.data.mapper

import com.ailyn.finanzasana.features.home.categoria.data.models.CategoriaDto
import com.ailyn.finanzasana.features.home.categoria.domain.entities.Categoria

fun CategoriaDto.toDomain() = Categoria(
    id = id,
    nombre = nombre
)
