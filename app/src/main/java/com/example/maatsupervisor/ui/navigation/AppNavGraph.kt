package com.example.maatsupervisor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maatsupervisor.ui.screens.HomeScreen
import com.example.maatsupervisor.ui.screens.LoginScreen
import com.example.maatsupervisor.ui.screens.ProfileScreen
import com.example.maatsupervisor.ui.screens.SplashScreen
import com.example.maatsupervisor.ui.screens.VehicleDashboardScreen

@Composable
fun AppNavGraph(
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(
                navController = navController,
                onThemeChange = onThemeChange
            )
        }
        composable("vehicles") {
            VehicleDashboardScreen()
        }

        composable("profile") {
            ProfileScreen(navController)
        }
    }
}

