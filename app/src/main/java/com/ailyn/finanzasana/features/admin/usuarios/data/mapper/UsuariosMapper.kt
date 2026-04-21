package com.ailyn.finanzasana.features.admin.usuarios.data.mapper

import com.ailyn.finanzasana.features.admin.usuarios.data.models.UsuarioAdminDto
import com.ailyn.finanzasana.features.admin.usuarios.domain.entities.UsuarioAdmin

fun UsuarioAdminDto.toDomain() = UsuarioAdmin(
    id = id,
    nombre = nombre,
    email = email,
    totalDeudas = totalDeudas
)
