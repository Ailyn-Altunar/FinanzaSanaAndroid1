package com.ailyn.finanzasana.features.home.deuda.domain.repositories

import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda
import com.ailyn.finanzasana.features.home.deuda.domain.entities.ResumenDeuda
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Abono

interface DeudaRepository {
    suspend fun getDeudas(): List<Deuda>
    suspend fun getResumenDeuda(): ResumenDeuda
    suspend fun getAbonos(idDeuda: Int): List<Abono>
    suspend fun registrarAbono(idDeuda: Int, monto: Double): Abono
    suspend fun liquidarDeuda(idDeuda: Int): Deuda
}
