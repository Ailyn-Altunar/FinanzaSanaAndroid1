package com.ailyn.finanzasana.features.auth.login.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("usuario") val usuario: UsuarioResponse
)
