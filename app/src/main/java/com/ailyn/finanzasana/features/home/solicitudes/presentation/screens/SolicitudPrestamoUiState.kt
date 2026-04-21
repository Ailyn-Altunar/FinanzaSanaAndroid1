package com.ailyn.finanzasana.features.home.solicitudes.presentation.screens

data class SolicitudPrestamoUiState(

    // ---------------------------
    // CAMPOS DEL FORMULARIO
    // ---------------------------
    val empresaId: Int? = null,
    val categoriaId: Int? = null,

    val montoSolicitado: String = "",
    val meses: String = "",
    val motivo: String = "",

    // Se muestra pero NO se edita (viene de Empresa)
    val tasaInteres: Double? = null,

    // ---------------------------
    // HARDWARE (FOTO + GPS)
    // ---------------------------
    val imagenBase64: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null,
    val direccion: String? = null,

    // ---------------------------
    // ESTADOS
    // ---------------------------
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null,

    // ---------------------------
    // ERRORES POR CAMPO
    // ---------------------------
    val errorEmpresa: String? = null,
    val errorCategoria: String? = null,
    val errorMonto: String? = null,
    val errorMeses: String? = null,
    val errorMotivo: String? = null,
    val errorUbicacion: String? = null
)
