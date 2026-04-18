package com.ailyn.finanzasana.features.admin.data.datasource

import com.ailyn.finanzasana.features.admin.data.model.AdminMetricsResponse
import com.ailyn.finanzasana.features.admin.data.model.ActividadAdminResponse
import com.ailyn.finanzasana.features.admin.data.model.UserAdminResponse

interface AdminDataSource {
    suspend fun getMetrics(): AdminMetricsResponse
    suspend fun getRecentActivity(): List<ActividadAdminResponse>
    suspend fun getUsers(): List<UserAdminResponse>
    suspend fun deleteUser(id: Int)
}