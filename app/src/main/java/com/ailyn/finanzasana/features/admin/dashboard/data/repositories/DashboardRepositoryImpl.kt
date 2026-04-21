package com.ailyn.finanzasana.features.admin.dashboard.data.repositories

import com.ailyn.finanzasana.core.session.SessionManager
import com.ailyn.finanzasana.features.admin.dashboard.data.datasource.api.DashboardApi
import com.ailyn.finanzasana.features.admin.dashboard.data.mapper.toDomain
import com.ailyn.finanzasana.features.admin.dashboard.domain.entities.DashboardData
import com.ailyn.finanzasana.features.admin.dashboard.domain.repositories.DashboardRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val api: DashboardApi,
    private val sessionManager: SessionManager
) : DashboardRepository {
    
    override suspend fun getDashboardData(): DashboardData = coroutineScope {
        val token = "Bearer ${sessionManager.getToken() ?: ""}"
        
        // Ejecutamos ambas peticiones en paralelo para mayor eficiencia
        val metricsDeferred = async { api.getMetrics(token) }
        val actividadDeferred = async { api.getActividad(token) }
        
        val metrics = metricsDeferred.await()
        val actividad = actividadDeferred.await()
        
        // Combinamos ambas respuestas en nuestra entidad de dominio
        DashboardData(
            usuariosTotales = metrics.usuariosTotales,
            montoGlobal = metrics.montoGlobal,
            deudasVencidas = metrics.deudasVencidas,
            ultimosAbonos = actividad.map { it.toDomain() }
        )
    }
}
