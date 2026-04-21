package com.ailyn.finanzasana.features.admin.dashboard.data.datasource.api

import com.ailyn.finanzasana.features.admin.dashboard.data.models.MetricsResponseDto
import com.ailyn.finanzasana.features.admin.dashboard.data.models.RegistroAbonoDto
import retrofit2.http.GET
import retrofit2.http.Header

interface DashboardApi {
    @GET("admin/metrics")
    suspend fun getMetrics(
        @Header("Authorization") token: String
    ): MetricsResponseDto

    @GET("admin/actividad")
    suspend fun getActividad(
        @Header("Authorization") token: String
    ): List<RegistroAbonoDto>
}
