package com.ailyn.finanzasana.features.admin.usuarios.domain.usecases

import com.ailyn.finanzasana.features.admin.usuarios.domain.entities.UsuarioAdmin
import com.ailyn.finanzasana.features.admin.usuarios.domain.repositories.UsuariosRepository
import javax.inject.Inject

class GetUsuariosUseCase @Inject constructor(
    private val repository: UsuariosRepository
) {
    suspend operator fun invoke(): List<UsuarioAdmin> = repository.getUsuarios()
}
