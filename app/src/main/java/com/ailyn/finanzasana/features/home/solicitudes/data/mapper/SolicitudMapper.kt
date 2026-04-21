package com.ailyn.finanzasana.features.home.solicitudes.data.mapper

import com.ailyn.finanzasana.features.home.solicitudes.data.models.CrearSolicitudRequestDto
import com.ailyn.finanzasana.features.home.solicitudes.domain.entities.NuevaSolicitud

fun NuevaSolicitud.toRequestDto(): CrearSolicitudRequestDto =
    CrearSolicitudRequestDto(
        idUsuario = idUsuario,
        idEmpresa = idEmpresa,
        montoSolicitado = montoSolicitado,
        meses = meses,
        motivo = motivo,
        idCategoria = idCategoria,
        imagenBase64 = imagenBase64,
        latitud = latitud,
        longitud = longitud
    )
