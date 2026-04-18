package com.ailyn.finanzasana.features.home.domain.repository

import com.ailyn.finanzasana.features.home.data.model.DeudaRequest
import com.ailyn.finanzasana.features.home.domain.model.Abono
import com.ailyn.finanzasana.features.home.domain.model.Deuda


interface DeudaRepository {

    suspend fun getDeudas(): Result<List<Deuda>>

    suspend fun getDeudaById(idDeuda: Int): Result<Deuda>

    suspend fun registrarAbono(idDeuda: Int, monto: Double): Result<Abono>

    suspend fun registrarDeuda(request: DeudaRequest): Result<Deuda>

    suspend fun actualizarDeuda(idDeuda: Int, request: DeudaRequest): Result<Boolean>

    suspend fun eliminarDeuda(idDeuda: Int): Result<Boolean>
}