package com.ailyn.finanzasana.core.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable object Login : Screen()
    @Serializable object Register : Screen()
    @Serializable object AdminDashboard : Screen()
    @Serializable object UserManagement : Screen()


    @Serializable object Deudas : Screen()

    @Serializable data class DetalleDeuda(val idDeuda: Int) : Screen()

    @Serializable object Planificador : Screen()
}