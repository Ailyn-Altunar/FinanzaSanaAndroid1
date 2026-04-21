package com.ailyn.finanzasana.features.admin.usuarios.data.models

import com.google.gson.annotations.SerializedName

data class UsuarioAdminDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("totalDeudas") val totalDeudas: Int
)
