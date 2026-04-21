package com.ailyn.finanzasana.features.home.deuda.data.mapper

import com.ailyn.finanzasana.features.home.deuda.data.models.AbonoResponse
import com.ailyn.finanzasana.features.home.deuda.data.models.DeudaResponse
import com.ailyn.finanzasana.features.home.deuda.data.models.ResumenDeudaResponse
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Abono
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda
import com.ailyn.finanzasana.features.home.deuda.domain.entities.ResumenDeuda

fun DeudaResponse.toDomain() = Deuda(
    id = id,
    concepto = concepto,
    montoOriginal = montoOriginal,
    saldoActual = saldoActual,
    porcentajePagado = porcentajePagado,
    fechaVencimiento = fechaVencimiento,
    tasaInteres = tasaInteres,
    categoriaNombre = categoria,
    idUsuario = 0, // No viene en el response de detalle
    estado = estadoDeuda,
    imagenBase64 = imagenBase64,
    latitud = latitud,
    longitud = longitud,
    abonos = abonos.map { it.toDomain() }
)

fun AbonoResponse.toDomain() = Abono(
    id = id,
    monto = monto,
    fecha = fecha
)

fun ResumenDeudaResponse.toDomain() = ResumenDeuda(
    totalAdeudado = totalAdeudadoActivo,
    cantidadDeudasActivas = cantidadDeudasActivas
)
