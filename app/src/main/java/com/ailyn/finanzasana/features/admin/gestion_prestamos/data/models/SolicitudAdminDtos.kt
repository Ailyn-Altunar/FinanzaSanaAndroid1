package com.ailyn.finanzasana.features.admin.gestion_prestamos.data.models

import com.google.gson.annotations.SerializedName

data class SolicitudAdminResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("nombreUsuario") val nombreUsuario: String,
    @SerializedName("nombreEmpresa") val nombreEmpresa: String,
    @SerializedName("montoSolicitado") val montoSolicitado: Double,
    @SerializedName("estado") val estado: String
)
