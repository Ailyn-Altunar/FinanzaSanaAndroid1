package com.ailyn.finanzasana.features.admin.dashboard.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ailyn.finanzasana.features.admin.dashboard.domain.entities.RegistroAbonoAdmin
import com.ailyn.finanzasana.features.admin.dashboard.presentation.viewmodel.DashboardViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onNavigateToUsuarios: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val blueColor = Color(0xFF2D5AF0)
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        viewModel.cargarDatos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Control", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        (context as? FragmentActivity)?.let { activity ->
                            viewModel.autenticarYAcceder(
                                activity = activity,
                                onSuccess = { onNavigateToUsuarios() }
                            )
                        } ?: run {
                            onNavigateToUsuarios()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Usuario",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blueColor)
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = blueColor)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF8F9FA))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MetricCard(
                            label = "Usuarios Totales",
                            value = uiState.data?.usuariosTotales?.toString() ?: "0",
                            modifier = Modifier.weight(1f)
                        )
                        val montoFormatted = formatMontoK(uiState.data?.montoGlobal ?: 0.0)
                        MetricCard(
                            label = "Monto Global",
                            value = montoFormatted,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Color(0xFFE57373),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "DEUDAS VENCIDAS",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            }
                            Text(
                                text = uiState.data?.deudasVencidas?.toString() ?: "0",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE57373)
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "ÚLTIMOS REGISTROS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                val abonos = uiState.data?.ultimosAbonos ?: emptyList()
                if (abonos.isEmpty()) {
                    item { Text("No hay abonos recientes", color = Color.Gray) }
                } else {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column {
                                abonos.forEachIndexed { index, abono ->
                                    RegistroAbonoItem(abono)
                                    if (index < abonos.size - 1) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            color = Color(0xFFF1F1F1)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MetricCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun RegistroAbonoItem(abono: com.ailyn.finanzasana.features.admin.dashboard.domain.entities.RegistroAbonoAdmin) {
    val blueColor = Color(0xFF2D5AF0)
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = abono.nombreUsuario, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = abono.fecha, fontSize = 11.sp, color = Color.Gray)
        }
        
        Spacer(Modifier.height(4.dp))
        
        Text(
            text = "Ha hecho un abono a su deuda",
            color = blueColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Monto: $${String.format(Locale.getDefault(), "%,.2f", abono.monto)}",
                fontSize = 12.sp,
                color = Color.DarkGray
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "ID Deuda: #${abono.idDeuda}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

private fun formatMontoK(monto: Double): String {
    return if (monto >= 1000) {
        "$${(monto / 1000).toInt()}k"
    } else {
        "$${monto.toInt()}"
    }
}
