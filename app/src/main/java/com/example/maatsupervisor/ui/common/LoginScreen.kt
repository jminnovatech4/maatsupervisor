package com.example.maatsupervisor.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.maatsupervisor.data.UserSession
import com.example.maatsupervisor.network.LoginApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {

    var gid by remember { mutableStateOf("") }
    var passcode by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val session = remember { UserSession(context) }
    val scope = rememberCoroutineScope()

    Scaffold { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Supervisor Login",
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    value = gid,
                    onValueChange = { gid = it },
                    label = { Text("GID") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = passcode,
                    onValueChange = { passcode = it },
                    label = { Text("Passcode") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                if (error.isNotEmpty()) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !loading,
                    onClick = {
                        loading = true
                        error = ""

                        LoginApi.login(gid, passcode) { success, response ->
                            loading = false

                            if (success && response?.optBoolean("status") == true) {
                                val data = response.getJSONObject("data")

                                scope.launch {
                                    session.saveLogin(
                                        gid = data.getString("gid"),
                                        name = data.getString("name"),
                                        designation = data.getString("designation")
                                    )

                                    when (data.getString("designation")) {
                                        "EME" -> navController.navigate("eme_home") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                        "PM" -> navController.navigate("pm_home")
                                        "RM" -> navController.navigate("rm_home")
                                    }
                                }
                            } else {
                                error = response?.optString("message") ?: "Login failed"
                            }
                        }
                    }
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Login")
                    }
                }
            }
        }
    }
}
