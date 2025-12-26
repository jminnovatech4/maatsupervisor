package com.example.maatsupervisor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.maatsupervisor.data.ThemeSession
import com.example.maatsupervisor.ui.navigation.AppNavGraph
import com.example.maatsupervisor.ui.theme.MaatsupervisorTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val themeSession = remember { ThemeSession(context) }
            val scope = rememberCoroutineScope()

            var isDark by remember { mutableStateOf(false) }

            // Load saved theme
            LaunchedEffect(Unit) {
                isDark = themeSession.isDark()
            }

            MaatsupervisorTheme(darkTheme = isDark) {
                AppNavGraph(
                    onThemeChange = { dark ->
                        isDark = dark
                        scope.launch {
                            themeSession.setDark(dark)
                        }
                    }
                )
            }
        }
    }
}
