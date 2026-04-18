package com.ailyn.finanzasana.features.home.data.mapper

import com.ailyn.finanzasana.features.home.data.model.DeudaResponse
import com.ailyn.finanzasana.features.home.data.model.AbonoResponse
import com.ailyn.finanzasana.features.home.domain.model.Deuda
import com.ailyn.finanzasana.features.home.domain.model.Abono




fun AbonoResponse.toDomain(): Abono {
    return Abono(
        id = this.id,
        monto = this.monto,
        fecha = this.fecha
    )
}


fun List<AbonoResponse>.toDomainList(): List<Abono> = this.map { it.toDomain() }



fun DeudaResponse.toDomain(): Deuda {
    return Deuda(
        id = this.id,
        concepto = this.concepto,
        montoOriginal = this.montoOriginal,
        saldoActual = this.saldoActual,
        porcentajePagado = this.porcentajePagado,
        tasaInteres = this.tasaInteres,
        fechaVencimiento = this.fechaVencimiento,
        categoria = this.categoria,
        fotoBase64 = this.fotoBase64,
        latitud = this.latitud,
        longitud = this.longitud,
        abonos = this.abonos.toDomainList()
    )
}


fun List<DeudaResponse>.toDomainListDeudas(): List<Deuda> = this.map { it.toDomain() }