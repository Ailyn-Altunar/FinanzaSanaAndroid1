package com.ailyn.finanzasana.features.home.deuda.data.models

import com.google.gson.annotations.SerializedName

data class DeudaResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("concepto") val concepto: String,
    @SerializedName("montoOriginal") val montoOriginal: Double,
    @SerializedName("saldoActual") val saldoActual: Double,
    @SerializedName("porcentajePagado") val porcentajePagado: Double,
    @SerializedName("tasaInteres") val tasaInteres: Double,
    @SerializedName("fechaVencimiento") val fechaVencimiento: String,
    @SerializedName("estadoDeuda") val estadoDeuda: String,
    @SerializedName("categoria") val categoria: String,
    @SerializedName("imagenBase64") val imagenBase64: String? = null,
    @SerializedName("latitud") val latitud: Double? = null,
    @SerializedName("longitud") val longitud: Double? = null,
    @SerializedName("abonos") val abonos: List<AbonoResponse> = emptyList()
)

data class ResumenDeudaResponse(
    @SerializedName("totalAdeudadoActivo") val totalAdeudadoActivo: Double,
    @SerializedName("cantidadDeudasActivas") val cantidadDeudasActivas: Int
)
