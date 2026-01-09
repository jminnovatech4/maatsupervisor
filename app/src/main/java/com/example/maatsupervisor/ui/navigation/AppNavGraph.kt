package com.example.maatsupervisor.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.maatsupervisor.ui.common.*
import com.example.maatsupervisor.ui.eme.screens.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(onThemeChange: (Boolean) -> Unit) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "splash") {

        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }

        composable("eme_home") {
            EmeHomeScreen(navController, onThemeChange)
        }

        composable("eme_vehicles") {
            VehicleDashboardScreen()
        }

        composable("pm_home") {
            Text("PM Dashboard")
        }

        composable("rm_home") {
            Text("RM Dashboard")
        }

        composable("profile") {
            ProfileScreen(navController)
        }
    }

}
