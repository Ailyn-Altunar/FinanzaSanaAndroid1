package com.ailyn.finanzasana.features.admin.dashboard.domain.usecases

import com.ailyn.finanzasana.features.admin.dashboard.domain.entities.DashboardData
import com.ailyn.finanzasana.features.admin.dashboard.domain.repositories.DashboardRepository
import javax.inject.Inject

class GetDashboardUseCase @Inject constructor(
    private val repository: DashboardRepository
) {
    suspend operator fun invoke(): DashboardData = repository.getDashboardData()
}
