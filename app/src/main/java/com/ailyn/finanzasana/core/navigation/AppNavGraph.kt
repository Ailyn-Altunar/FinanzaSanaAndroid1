package com.ailyn.finanzasana.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.hilt.navigation.compose.hiltViewModel
import com.ailyn.finanzasana.features.auth.login.presentation.screens.LoginScreen
import com.ailyn.finanzasana.features.auth.login.presentation.screens.RegisterScreen
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.ailyn.finanzasana.features.home.deuda.presentation.screens.DetalleDeudaScreen
import com.ailyn.finanzasana.features.home.deuda.presentation.screens.MisFinanzasScreen
import com.ailyn.finanzasana.features.home.deuda.domain.entities.Deuda
import com.ailyn.finanzasana.features.home.solicitudes.presentation.screens.SolicitudPrestamoScreen
import com.ailyn.finanzasana.features.home.empresas.presentation.viewmodel.EmpresasViewModel
import com.ailyn.finanzasana.features.home.categoria.presentation.viewmodel.CategoriasViewModel
import com.ailyn.finanzasana.features.admin.gestion_prestamos.presentation.screens.GestionPrestamosScreen
import com.ailyn.finanzasana.features.admin.gestion_prestamos.presentation.viewmodel.GestionPrestamosViewModel
import com.ailyn.finanzasana.core.session.SessionManager
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppNavGraph(
    navController: NavHostController,
    sessionManager: SessionManager // Lo pasamos inyectado
) {
    // Determinar el destino inicial según la sesión y el rol
    val startDest: Any = remember {
        if (sessionManager.isLoggedIn()) {
            val userId = sessionManager.getUserId()
            val rol = sessionManager.getRol()
            if (rol == 1) {
                GestionPrestamos(userId)
            } else {
                MisFinanzas(userId)
            }
        } else {
            Login
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {

        composable<Login> {
            LoginScreen(
                onLoginSuccess = { rol ->
                    // IMPORTANTE: Obtenemos los datos actualizados de la sesión que guardó el UseCase
                    val userId = sessionManager.getUserId()

                    if (rol == 1) {
                        navController.navigate(GestionPrestamos(userId)) {
                            popUpTo(Login) { inclusive = true }
                        }
                    } else {
                        navController.navigate(MisFinanzas(userId)) {
                            popUpTo(Login) { inclusive = true }
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Register)
                }
            )
        }

        composable<Register> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable<GestionPrestamos> {
            val viewModel: GestionPrestamosViewModel = hiltViewModel()
            GestionPrestamosScreen(
                viewModel = viewModel,
                onBack = { /* Opcional: Cerrar app o mostrar diálogo */ },
                onLogout = {
                    sessionManager.clearSession()
                    navController.navigate(Login) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable<MisFinanzas> {
            var selectedDeuda by remember { mutableStateOf<Deuda?>(null) }

            if (selectedDeuda == null) {
                MisFinanzasScreen(
                    onNavigateToSolicitud = {
                        navController.navigate(Solicitudes(idUsuario = sessionManager.getUserId()))
                    },
                    onNavigateToDetalle = { deuda ->
                        selectedDeuda = deuda
                    },
                    onLogout = {
                        sessionManager.clearSession()
                        navController.navigate(Login) {
                            popUpTo(0)
                        }
                    }
                )
            } else {
                DetalleDeudaScreen(
                    deuda = selectedDeuda!!,
                    onBack = { selectedDeuda = null }
                )
            }
        }

        composable<Solicitudes> { backStackEntry ->
            val route: Solicitudes = backStackEntry.toRoute()
            
            val empresasVm: EmpresasViewModel = hiltViewModel()
            val categoriasVm: CategoriasViewModel = hiltViewModel()

            SolicitudPrestamoScreen(
                empresas = empresasVm.empresas,
                categorias = categoriasVm.categorias,
                idUsuario = route.idUsuario,
                onBack = { navController.popBackStack() },
                onSuccess = { 
                    navController.popBackStack()
                }
            )
        }
    }
}
