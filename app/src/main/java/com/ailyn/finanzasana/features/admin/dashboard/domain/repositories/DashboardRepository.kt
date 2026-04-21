package com.ailyn.finanzasana.features.admin.dashboard.domain.repositories

import com.ailyn.finanzasana.features.admin.dashboard.domain.entities.DashboardData

interface DashboardRepository {
    suspend fun getDashboardData(): DashboardData
}
