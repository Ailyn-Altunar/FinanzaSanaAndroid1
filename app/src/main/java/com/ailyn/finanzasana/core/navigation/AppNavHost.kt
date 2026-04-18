package com.ailyn.finanzasana.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ailyn.finanzasana.features.admin.presentation.screens.AdminDashboardScreen
import com.ailyn.finanzasana.features.admin.presentation.screens.UserManagementScreen
import com.ailyn.finanzasana.features.auth.presentation.screens.LoginScreen
import com.ailyn.finanzasana.features.auth.presentation.screens.RegisterScreen
import com.ailyn.finanzasana.features.home.presentation.screens.DeudasScreen
import com.ailyn.finanzasana.features.home.presentation.screens.DetalleDeudaScreen
import com.ailyn.finanzasana.features.home.presentation.viewmodel.DeudasViewModel
import com.ailyn.finanzasana.features.planificador.presentation.screens.PlanificadorScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login
    ) {

        composable<Screen.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Deudas) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register)
                }
            )
        }

        composable<Screen.Register> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Deudas> { backStackEntry ->
            val deudasViewModel: DeudasViewModel = hiltViewModel(backStackEntry)

            val currentEntry = navController.currentBackStackEntryAsState()
            LaunchedEffect(currentEntry.value) {
                val currentRoute = currentEntry.value?.destination?.route
                if (currentRoute?.contains("Deudas") == true) {
                    deudasViewModel.cargarDeudas()
                }
            }

            LaunchedEffect(Unit) {
                deudasViewModel.logoutEvent.collect {
                    navController.navigate(Screen.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

            DeudasScreen(
                viewModel = deudasViewModel,
                onUserClick = {
                    navController.navigate(Screen.AdminDashboard)
                },
                onDeudaClick = { id ->
                    navController.navigate(Screen.DetalleDeuda(idDeuda = id))
                },
                onNavigateToPlanificador = {
                    navController.navigate(Screen.Planificador)
                },
                onCerrarSesion = {
                    deudasViewModel.cerrarSesion()
                }
            )
        }

        composable<Screen.DetalleDeuda> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.DetalleDeuda>()
            DetalleDeudaScreen(
                idDeuda = args.idDeuda,
                onBack = { navController.popBackStack() }
            )
        }

        composable<Screen.Planificador> {
            PlanificadorScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<Screen.AdminDashboard> {
            AdminDashboardScreen(
                onNavigateToUsers = {
                    navController.navigate(Screen.UserManagement)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.UserManagement> {
            UserManagementScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}