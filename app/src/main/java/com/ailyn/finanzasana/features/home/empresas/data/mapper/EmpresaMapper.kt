package com.ailyn.finanzasana.features.home.empresas.data.mapper

import com.ailyn.finanzasana.features.home.empresas.data.models.EmpresaDto
import com.ailyn.finanzasana.features.home.empresas.domain.entities.Empresa

fun EmpresaDto.toDomain() = Empresa(
    id = id,
    nombre = nombre,
    tasaInteres = tasaInteres
)
