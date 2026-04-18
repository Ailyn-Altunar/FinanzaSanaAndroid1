package com.ailyn.finanzasana.features.admin.domain.usecase

import com.ailyn.finanzasana.features.admin.data.model.UserAdminResponse
import com.ailyn.finanzasana.features.admin.domain.repository.AdminRepository
import javax.inject.Inject

class GetAdminUsersUseCase @Inject constructor(
    private val repository: AdminRepository
) {
    suspend operator fun invoke(): Result<List<UserAdminResponse>> {
        return repository.getUsers()
    }
}