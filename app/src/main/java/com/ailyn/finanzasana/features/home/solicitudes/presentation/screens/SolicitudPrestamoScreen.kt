package com.ailyn.finanzasana.features.home.solicitudes.presentation.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ailyn.finanzasana.features.home.empresas.domain.entities.Empresa
import com.ailyn.finanzasana.features.home.categoria.domain.entities.Categoria
import com.ailyn.finanzasana.features.home.solicitudes.presentation.viewmodel.SolicitudPrestamoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudPrestamoScreen(
    empresas: List<Empresa>,
    categorias: List<Categoria>,
    idUsuario: Int,
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: SolicitudPrestamoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val blueColor = Color(0xFF2D5AF0)
    val lightGray = Color(0xFFF5F7FA)

    // Launcher para la Cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            viewModel.onImagenSeleccionada(bitmap)
        }
    }

    // Launcher para Permisos de Cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch()
        } else {
            viewModel.onPermissionDenied("Cámara")
        }
    }

    // Launcher para Permisos de Ubicación
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            viewModel.obtenerUbicacion()
        }
    }

    LaunchedEffect(empresas, categorias) {
        viewModel.setEmpresas(empresas)
        viewModel.setCategorias(categorias)
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            onSuccess()
            viewModel.resetSuccess()
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Solicitud Prestamo",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = blueColor
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ---------------------------
            // EMPRESA
            // ---------------------------
            var empresaExpanded by remember { mutableStateOf(false) }
            val empresaSeleccionada = empresas.find { it.id == state.empresaId }

            CustomDropdownField(
                label = "Empresa",
                value = empresaSeleccionada?.nombre ?: "",
                onClick = { empresaExpanded = true },
                isError = state.errorEmpresa != null
            )

            DropdownMenu(
                expanded = empresaExpanded,
                onDismissRequest = { empresaExpanded = false },
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                empresas.forEach { empresa ->
                    DropdownMenuItem(
                        text = { Text(empresa.nombre) },
                        onClick = {
                            viewModel.onEmpresaSeleccionada(empresa.id)
                            empresaExpanded = false
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------------------------
            // MONTO
            // ---------------------------
            CustomTextField(
                label = "Monto",
                value = state.montoSolicitado,
                onValueChange = viewModel::onMontoChange,
                keyboardType = KeyboardType.Number,
                isError = state.errorMonto != null
            )

            Spacer(Modifier.height(16.dp))

            // ---------------------------
            // TASA DE INTERÉS
            // ---------------------------
            CustomTextField(
                label = "Tasa (%)",
                value = state.tasaInteres?.toString() ?: "",
                onValueChange = {},
                readOnly = true
            )

            Spacer(Modifier.height(16.dp))

            // ---------------------------
            // CATEGORÍA
            // ---------------------------
            var categoriaExpanded by remember { mutableStateOf(false) }
            val categoriaSeleccionada = categorias.find { it.id == state.categoriaId }

            CustomDropdownField(
                label = "Categoría",
                value = categoriaSeleccionada?.nombre ?: "",
                onClick = { categoriaExpanded = true },
                isError = state.errorCategoria != null
            )

            DropdownMenu(
                expanded = categoriaExpanded,
                onDismissRequest = { categoriaExpanded = false },
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria.nombre) },
                        onClick = {
                            viewModel.onCategoriaSeleccionada(categoria.id)
                            categoriaExpanded = false
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------------------------
            // VENCIMIENTO (Meses)
            // ---------------------------
            CustomTextField(
                label = "Vencimiento (Meses)",
                value = state.meses,
                onValueChange = viewModel::onMesesChange,
                keyboardType = KeyboardType.Number,
                isError = state.errorMeses != null
            )

            Spacer(Modifier.height(16.dp))

            // ---------------------------
            // MOTIVO (NUEVO)
            // ---------------------------
            CustomTextField(
                label = "Motivo del préstamo",
                value = state.motivo,
                onValueChange = viewModel::onMotivoChange,
                isError = state.errorMotivo != null
            )

            Spacer(Modifier.height(20.dp))

            // ---------------------------
            // EVIDENCIA
            // ---------------------------
            Text(
                "Evidencia (Opcional)",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                fontSize = 14.sp
            )
            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(lightGray, RoundedCornerShape(12.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    .clickable {
                        viewModel.solicitarAperturaCamara(
                            onLaunchCamera = { cameraLauncher.launch() },
                            onAskPermission = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, tint = blueColor)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (state.imagenBase64 != null) "Foto Capturada" else "Tomar Foto del Objeto",
                        color = blueColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------------------------
            // UBICACIÓN
            // ---------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        locationPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = state.direccion ?: "Obtener ubicación actual",
                    color = Color(0xFF4CAF50),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            Spacer(Modifier.height(32.dp))

            // ---------------------------
            // BOTÓN GUARDAR
            // ---------------------------
            Button(
                onClick = { viewModel.enviarSolicitud(idUsuario) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                shape = RoundedCornerShape(25.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text("Enviar Solicitud", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        readOnly = readOnly,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF2D5AF0)
        )
    )
}

@Composable
fun CustomDropdownField(
    label: String,
    value: String,
    onClick: () -> Unit,
    isError: Boolean = false
) {
    Box(modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            shape = RoundedCornerShape(8.dp),
            isError = isError,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color(0xFF2D5AF0)
            )
        )
        // Capa invisible para detectar el clic en todo el campo
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { onClick() }
        )
    }
}
