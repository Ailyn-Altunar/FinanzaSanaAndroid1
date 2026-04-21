package com.ailyn.finanzasana.features.auth.login.data.models

import com.google.gson.annotations.SerializedName

data class UsuarioRequest(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("contrasena") val contrasena: String,
    @SerializedName("idRol") val idRol: Int,
    @SerializedName("telefono") val telefono: String
)

data class UsuarioResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("rol") val idRol: Int,
    @SerializedName("telefono") val telefono: String
)
