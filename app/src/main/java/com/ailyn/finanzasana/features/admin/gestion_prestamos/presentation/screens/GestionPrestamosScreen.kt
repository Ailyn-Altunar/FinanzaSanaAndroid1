package com.ailyn.finanzasana.features.admin.gestion_prestamos.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ailyn.finanzasana.features.admin.gestion_prestamos.domain.entities.SolicitudAdmin
import com.ailyn.finanzasana.features.admin.gestion_prestamos.presentation.viewmodel.GestionPrestamosViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionPrestamosScreen(
    viewModel: GestionPrestamosViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // REFRESCAR AL ENTRAR
    LaunchedEffect(Unit) {
        viewModel.cargarDatos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Préstamos", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Ir a ajustes */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes", tint = Color.White)
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2D5AF0))
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF0F2F5))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // SECCIÓN: NUEVAS SOLICITUDES
            item {
                Text(
                    text = "NUEVAS SOLICITUDES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }

            if (uiState.pendientes.isEmpty()) {
                item { Text("No hay solicitudes pendientes", modifier = Modifier.padding(8.dp)) }
            } else {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            uiState.pendientes.forEach { solicitud ->
                                ItemSolicitudNueva(
                                    solicitud = solicitud,
                                    onAceptar = { viewModel.aprobarSolicitud(solicitud.id) },
                                    onRechazar = { viewModel.rechazarSolicitud(solicitud.id) }
                                )
                                if (solicitud != uiState.pendientes.last()) HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }
                }
            }

            // SECCIÓN: HISTORIAL DE DECISIONES
            item {
                Text(
                    text = "HISTORIAL DE DECISIONES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        uiState.historial.forEach { solicitud ->
                            ItemHistorial(solicitud = solicitud)
                            if (solicitud != uiState.historial.last()) HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemSolicitudNueva(
    solicitud: SolicitudAdmin,
    onAceptar: () -> Unit,
    onRechazar: () -> Unit
) {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = solicitud.nombreUsuario, 
                fontWeight = FontWeight.Bold, 
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = format.format(solicitud.montoSolicitado), 
                fontWeight = FontWeight.Bold, 
                fontSize = 18.sp, 
                color = Color(0xFF2D5AF0) // Azul de la imagen
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onAceptar,
                modifier = Modifier.weight(1f).height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F5E9)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("Aceptar", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Button(
                onClick = onRechazar,
                modifier = Modifier.weight(1f).height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("Rechazar", color = Color(0xFFC62828), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun ItemHistorial(solicitud: SolicitudAdmin) {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    val esAprobada = solicitud.estado == "APROBADA" || solicitud.estado == "aprobada"
    val colorEstado = if (esAprobada) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val colorTexto = if (esAprobada) Color(0xFF2E7D32) else Color(0xFFC62828)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = solicitud.nombreUsuario, 
                fontWeight = FontWeight.Bold, 
                fontSize = 17.sp,
                color = Color.Black
            )
            Text(
                text = format.format(solicitud.montoSolicitado), 
                fontSize = 14.sp, 
                color = Color.Gray
            )
        }
        Surface(
            color = colorEstado,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = solicitud.estado.uppercase(),
                color = colorTexto,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}
