package com.ailyn.finanzasana.features.home.deuda.data.models

import com.google.gson.annotations.SerializedName

data class AbonoRequest(
    @SerializedName("monto") val monto: Double
)

data class AbonoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("monto") val monto: Double,
    @SerializedName("fecha") val fecha: String
)
