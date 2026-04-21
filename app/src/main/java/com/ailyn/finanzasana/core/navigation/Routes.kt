package com.ailyn.finanzasana.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
data class AdminHome(val idUsuario: Int)

@Serializable
data class GestionPrestamos(val idUsuario: Int)

@Serializable
data class Solicitudes(val idUsuario: Int)

@Serializable
data class MisFinanzas(val idUsuario: Int)
