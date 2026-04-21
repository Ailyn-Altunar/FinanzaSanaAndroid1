package com.ailyn.finanzasana.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ailyn.finanzasana.core.session.SessionManager

@Composable
fun AppNavHost(sessionManager: SessionManager) {
    val navController = rememberNavController()
    AppNavGraph(navController, sessionManager)
}
