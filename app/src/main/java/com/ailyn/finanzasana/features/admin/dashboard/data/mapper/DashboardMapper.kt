package com.ailyn.finanzasana.features.admin.dashboard.data.mapper

import com.ailyn.finanzasana.features.admin.dashboard.data.models.MetricsResponseDto
import com.ailyn.finanzasana.features.admin.dashboard.data.models.RegistroAbonoDto
import com.ailyn.finanzasana.features.admin.dashboard.domain.entities.RegistroAbonoAdmin



fun RegistroAbonoDto.toDomain() = RegistroAbonoAdmin(
    nombreUsuario = nombreUsuario,
    monto = montoAbono,
    idDeuda = idDeuda,
    fecha = fecha.substringBefore("T") // Toma solo la fecha antes de la 'T' o el espacio
)
