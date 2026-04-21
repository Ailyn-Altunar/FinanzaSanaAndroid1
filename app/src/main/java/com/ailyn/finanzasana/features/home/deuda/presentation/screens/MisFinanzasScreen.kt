package com.ailyn.finanzasana.features.home.deuda.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda
import com.ailyn.finanzasana.features.home.deuda.presentation.viewmodel.DeudaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisFinanzasScreen(
    onNavigateToSolicitud: () -> Unit,
    onNavigateToDetalle: (Deuda) -> Unit,
    onLogout: () -> Unit,
    viewModel: DeudaViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val blueColor = Color(0xFF2D5AF0)

    // REFRESCAR AL ENTRAR
    LaunchedEffect(Unit) {
        viewModel.cargarDatos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Finanzas", color = Color.White, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blueColor)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToSolicitud,
                containerColor = blueColor,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Solicitud")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F7FA))
        ) {
            // Card de Resumen (Total Adeudado)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Total Adeudado", color = Color.Gray, fontSize = 14.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "$${String.format("%,.2f", state.totalAdeudado)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = blueColor
                    )
                    Text(
                        text = "${state.cantidadDeudasActivas} deudas activas",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            // Lista de Deudas
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.deudas) { deuda ->
                    DeudaItem(
                        deuda = deuda,
                        onClick = { onNavigateToDetalle(deuda) }
                    )
                }
            }
        }
    }
}

@Composable
fun DeudaItem(
    deuda: Deuda,
    onClick: () -> Unit
) {
    val blueColor = Color(0xFF2D5AF0)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = deuda.concepto,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red, // Según tu imagen, el nombre es rojo
                    fontSize = 16.sp
                )
                Text(
                    text = deuda.categoriaNombre,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${String.format("%,.2f", deuda.saldoActual)}",
                    fontWeight = FontWeight.Bold,
                    color = blueColor,
                    fontSize = 16.sp
                )
                Text(
                    text = deuda.fechaVencimiento,
                    color = Color.Red, // Según tu imagen, la fecha es roja
                    fontSize = 12.sp
                )
            }
        }
    }
}
