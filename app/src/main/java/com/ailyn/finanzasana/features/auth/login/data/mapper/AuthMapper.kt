package com.ailyn.finanzasana.features.auth.login.data.mapper

import com.ailyn.finanzasana.features.auth.login.data.models.LoginResponse
import com.ailyn.finanzasana.features.auth.login.data.models.UsuarioResponse
import com.ailyn.finanzasana.features.auth.login.domain.entities.Login
import com.ailyn.finanzasana.features.auth.login.domain.entities.Usuario

fun UsuarioResponse.toDomain(): Usuario = Usuario(
    id       = id,
    nombre   = nombre,
    email    = email,
    idRol    = idRol,
    telefono = telefono
)

fun LoginResponse.toDomain(): Login = Login(
    usuario = usuario.toDomain(),
    token   = token
)
