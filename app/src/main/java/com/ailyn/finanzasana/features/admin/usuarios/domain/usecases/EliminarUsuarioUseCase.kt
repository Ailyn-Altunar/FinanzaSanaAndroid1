package com.ailyn.finanzasana.features.admin.usuarios.domain.usecases

import com.ailyn.finanzasana.features.admin.usuarios.domain.repositories.UsuariosRepository
import javax.inject.Inject

class EliminarUsuarioUseCase @Inject constructor(
    private val repository: UsuariosRepository
) {
    suspend operator fun invoke(id: Int) = repository.eliminarUsuario(id)
}
