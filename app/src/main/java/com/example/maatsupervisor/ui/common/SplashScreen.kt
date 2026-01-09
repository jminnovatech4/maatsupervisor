package com.example.maatsupervisor.ui.common

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.maatsupervisor.data.UserSession
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val context = LocalContext.current
    val session = remember { UserSession(context) }

    LaunchedEffect(Unit) {
        try {
            val isLogged = session.isLoggedIn()
            Log.d("SPLASH", "Splash launched")

            if (isLogged) {
                val role = session.getDesignation()

                when (role) {
                    "EME" -> navController.navigate("eme_home") {
                        popUpTo("splash") { inclusive = true }
                    }
                    "PM" -> navController.navigate("pm_home") {
                        popUpTo("splash") { inclusive = true }
                    }
                    "RM" -> navController.navigate("rm_home") {
                        popUpTo("splash") { inclusive = true }
                    }
                    else -> navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            } else {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        } catch (e: Exception) {
            // fallback always to login
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}
