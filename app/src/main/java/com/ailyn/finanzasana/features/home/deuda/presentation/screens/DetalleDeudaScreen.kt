package com.ailyn.finanzasana.features.home.deuda.presentation.screens

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda
import com.ailyn.finanzasana.features.home.deuda.presentation.viewmodel.DetalleDeudaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleDeudaScreen(
    deuda: Deuda,
    onBack: () -> Unit,
    viewModel: DetalleDeudaViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val blueColor = Color(0xFF2D5AF0)
    val redColor = Color.Red
    
    var showAbonoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(deuda) {
        viewModel.setDeuda(deuda)
    }

    val currentDeuda = state.deuda ?: deuda

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Deuda", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blueColor)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = currentDeuda.concepto,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = blueColor
                    )
                    Text(text = currentDeuda.categoriaNombre, color = Color.Gray, fontSize = 14.sp)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFFF0F0F0))
                    Spacer(modifier = Modifier.height(16.dp))

                    val esVencida = try {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val fechaVenc = sdf.parse(currentDeuda.fechaVencimiento)
                        fechaVenc?.before(Date()) ?: false
                    } catch (e: Exception) { false }

                    DetailRow(
                        label = "Vencimiento:", 
                        value = if (esVencida) "${currentDeuda.fechaVencimiento} (¡VENCIDA!)" else currentDeuda.fechaVencimiento,
                        valueColor = if (esVencida) redColor else Color.DarkGray
                    )

                    DetailRow("Tasa de interés:", "${currentDeuda.tasaInteres}% anual")
                    
                    val ubicacionText = if (currentDeuda.latitud != null) 
                        "${String.format("%.4f", currentDeuda.latitud)}, ${String.format("%.4f", currentDeuda.longitud)}" 
                        else "No disponible"
                    DetailRow("Ubicación:", ubicacionText)

                    currentDeuda.imagenBase64?.let { base64 ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Evidencia:", color = Color.Gray, fontSize = 14.sp)
                        val bitmap = remember(base64) {
                            try {
                                val imageBytes = Base64.decode(base64, Base64.DEFAULT)
                                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            } catch (e: Exception) { null }
                        }
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "Evidencia",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .padding(top = 8.dp)
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Saldo actual", color = Color.Gray, fontSize = 14.sp)
                    Text(
                        text = "$${String.format("%,.2f", currentDeuda.saldoActual)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = blueColor
                    )
                    Text(
                        text = "de $${String.format("%,.2f", currentDeuda.montoOriginal)} original",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Progreso de pago", color = Color.LightGray, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val progresoFloat = (currentDeuda.porcentajePagado / 100).toFloat()
                    
                    LinearProgressIndicator(
                        progress = { progresoFloat },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = blueColor,
                        trackColor = Color(0xFFE0E0E0),
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text("${currentDeuda.porcentajePagado.toInt()}% liquidado", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Historial de Abonos", color = Color.LightGray, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (currentDeuda.abonos.isEmpty()) {
                        Text("Aún no has registrado pagos", color = Color.Gray)
                    } else {
                        currentDeuda.abonos.forEach { abono ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("Abono realizado", fontWeight = FontWeight.Medium)
                                    Text(abono.fecha, fontSize = 12.sp, color = Color.Gray)
                                }
                                Text(
                                    text = "+ $${String.format("%,.2f", abono.monto)}",
                                    color = Color(0xFF4CAF50),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            if (abono != currentDeuda.abonos.last()) {
                                HorizontalDivider(color = Color(0xFFF0F0F0))
                            }
                        }
                    }
                }
            }

            Button(
                onClick = { showAbonoDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Registrar Abono", fontSize = 18.sp)
            }

            var showLiquidarConfirm by remember { mutableStateOf(false) }
            
            OutlinedButton(
                onClick = { showLiquidarConfirm = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(56.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, blueColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = blueColor)
                Spacer(Modifier.width(8.dp))
                Text("Liquidar Deuda", fontSize = 18.sp, color = blueColor)
            }

            if (showLiquidarConfirm) {
                AlertDialog(
                    onDismissRequest = { showLiquidarConfirm = false },
                    title = { Text("¿Liquidar deuda?") },
                    text = { Text("Se registrará un pago por el total del saldo pendiente ($${String.format("%,.2f", currentDeuda.saldoActual)}).") },
                    confirmButton = {
                        TextButton(onClick = { 
                            viewModel.liquidarDeuda()
                            showLiquidarConfirm = false 
                        }) {
                            Text("Confirmar", color = blueColor, fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLiquidarConfirm = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                    }
                )
            }
        }
    }

    if (showAbonoDialog) {
        AbonoDialog(
            maxMonto = currentDeuda.saldoActual,
            onDismiss = { showAbonoDialog = false },
            onConfirm = { monto ->
                viewModel.registrarAbono(monto)
                showAbonoDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbonoDialog(
    maxMonto: Double,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var montoText by remember { mutableStateOf("") }
    val blueColor = Color(0xFF2D5AF0)

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Monto a abonar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = montoText,
                    onValueChange = { 
                        if (it.isEmpty() || it.replace(",", ".").toDoubleOrNull() != null) {
                            montoText = it 
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("$ 0.00", color = Color.LightGray) },
                    leadingIcon = { 
                        Text("$", color = blueColor, fontWeight = FontWeight.Bold, fontSize = 20.sp) 
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Text(
                    text = "Máximo: $${String.format("%,.2f", maxMonto)}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text("Cancelar", color = blueColor)
                    }

                    Button(
                        onClick = { 
                            val monto = montoText.replace(",", ".").toDoubleOrNull() ?: 0.0
                            if (monto > 0 && monto <= maxMonto) {
                                onConfirm(monto)
                            }
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9EBAFD))
                    ) {
                        Text("Confirmar", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, valueColor: Color = Color.DarkGray) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "$label ", color = Color.Gray, modifier = Modifier.width(120.dp))
        Text(text = value, color = valueColor, fontWeight = FontWeight.Medium)
    }
}
