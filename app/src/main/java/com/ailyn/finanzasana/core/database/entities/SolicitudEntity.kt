package com.ailyn.finanzasana.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class SolicitudEntity(
    @PrimaryKey val id: Int,
    val nombreUsuario: String,
    val nombreEmpresa: String,
    val montoSolicitado: Double,
    val meses: Int,
    val motivo: String,
    val tasaInteres: Double,
    val estado: Int,
    val fechaSolicitud: String,
    val categoriaId: Int
)
