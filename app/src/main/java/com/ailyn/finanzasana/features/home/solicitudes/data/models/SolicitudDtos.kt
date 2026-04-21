package com.ailyn.finanzasana.features.home.solicitudes.data.models

import com.google.gson.annotations.SerializedName

data class CrearSolicitudRequestDto(
    @SerializedName("idUsuario")      val idUsuario: Int,
    @SerializedName("idEmpresa")      val idEmpresa: Int,
    @SerializedName("montoSolicitado") val montoSolicitado: Double,
    @SerializedName("meses")          val meses: Int,
    @SerializedName("motivo")         val motivo: String,
    @SerializedName("idCategoria")    val idCategoria: Int,
    @SerializedName("imagenBase64")   val imagenBase64: String?,
    @SerializedName("latitud")        val latitud: Double?,
    @SerializedName("longitud")       val longitud: Double?
)

data class SolicitudPrestamoDto(
    @SerializedName("id")              val id: Int,
    @SerializedName("idUsuario")       val idUsuario: Int,
    @SerializedName("idEmpresa")       val idEmpresa: Int,
    @SerializedName("montoSolicitado") val montoSolicitado: Double,
    @SerializedName("meses")           val meses: Int,
    @SerializedName("motivo")          val motivo: String,
    @SerializedName("tasaInteres")     val tasaInteres: Double,
    @SerializedName("idCategoria")     val idCategoria: Int,
    @SerializedName("imagenBase64")    val imagenBase64: String?,
    @SerializedName("latitud")         val latitud: Double?,
    @SerializedName("longitud")        val longitud: Double?,
    @SerializedName("estado")          val estado: Int,
    @SerializedName("fechaSolicitud")  val fechaSolicitud: String
)
