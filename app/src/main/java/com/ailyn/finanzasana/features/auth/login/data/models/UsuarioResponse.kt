package com.ailyn.finanzasana.features.auth.login.data.models

import com.google.gson.annotations.SerializedName

data class UsuarioResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("rol") val idRol: Int, // <--- "rol" en JSON, idRol en Android
    @SerializedName("telefono") val telefono: String
)
