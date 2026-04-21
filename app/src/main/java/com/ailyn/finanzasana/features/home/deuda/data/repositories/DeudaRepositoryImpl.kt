package com.ailyn.finanzasana.features.home.deuda.data.repositories

import com.ailyn.finanzasana.features.home.deuda.data.datasource.api.DeudaApi
import com.ailyn.finanzasana.features.home.deuda.data.mapper.toDomain
import com.ailyn.finanzasana.features.home.deuda.data.models.AbonoRequest
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Abono
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda
import com.ailyn.finanzasana.features.home.deuda.domain.entities.ResumenDeuda
import com.ailyn.finanzasana.features.home.deuda.domain.repositories.DeudaRepository
import javax.inject.Inject

class DeudaRepositoryImpl @Inject constructor(
    private val api: DeudaApi
) : DeudaRepository {
    override suspend fun getDeudas(): List<Deuda> {
        return api.getDeudas().map { it.toDomain() }
    }

    override suspend fun getResumenDeuda(): ResumenDeuda {
        return api.getResumenDeuda().toDomain()
    }

    override suspend fun getAbonos(idDeuda: Int): List<Abono> {
        return api.getAbonosByDeuda(idDeuda).map { it.toDomain() }
    }

    override suspend fun registrarAbono(idDeuda: Int, monto: Double): Abono {
        return api.registrarAbono(idDeuda, AbonoRequest(monto)).toDomain()
    }

    override suspend fun liquidarDeuda(idDeuda: Int): Deuda {
        return api.liquidarDeuda(idDeuda).toDomain()
    }
}
