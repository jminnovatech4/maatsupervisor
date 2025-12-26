package com.example.maatsupervisor.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.maatsupervisor.data.ThemeSession
import com.example.maatsupervisor.data.UserSession
import com.example.maatsupervisor.data.model.DashboardSummary
import com.example.maatsupervisor.data.model.TodayCaseDeny
import com.example.maatsupervisor.network.CaseDenyApi
import com.example.maatsupervisor.network.DashboardSummaryApi
import com.example.maatsupervisor.ui.components.CaseDenyActionSheet
import com.example.maatsupervisor.ui.components.CaseDenyBottomSheet
import com.example.maatsupervisor.ui.components.CaseDenyCard
import com.example.maatsupervisor.ui.components.TodayCaseDenyList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onThemeChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val session = remember { UserSession(context) }
    val themeSession = remember { ThemeSession(context) }
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var darkMode by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    var summary by remember { mutableStateOf<DashboardSummary?>(null) }
    var todayCaseDeny by remember { mutableStateOf<List<TodayCaseDeny>>(emptyList()) }

    // ⭐ SINGLE SOURCE OF TRUTH FOR MODAL
    var selectedCase by remember { mutableStateOf<TodayCaseDeny?>(null) }

    /* ---------------- LOAD DATA ---------------- */
    LaunchedEffect(Unit) {
        name = session.getUserName()
        position = session.getDesignation()
        darkMode = themeSession.isDark()

        val gid = session.getGid()

        DashboardSummaryApi.fetchSummary(gid) {
            summary = it
        }

        CaseDenyApi.fetchTodayCaseDeny(gid) { success, list ->
            if (success) todayCaseDeny = list
        }
    }

    /* ---------------- DRAWER ---------------- */
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Column(Modifier.padding(16.dp)) {
                    Text(name, style = MaterialTheme.typography.titleLarge)
                    Text(position, style = MaterialTheme.typography.bodyMedium)
                }

                Divider()

                NavigationDrawerItem(
                    label = { Text("Profile") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("profile")
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Vehicles") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("vehicles")
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dark Mode")
                    Switch(
                        checked = darkMode,
                        onCheckedChange = {
                            darkMode = it
                            onThemeChange(it)
                        }
                    )
                }

                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = { showLogoutDialog = true }
                )
            }
        }
    ) {

        /* ---------------- MAIN CONTENT ---------------- */
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // 🔹 Dashboard summary
                item {
                    summary?.let {
                        DashboardCards(it)
                    } ?: Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // 🔹 Title
                if (todayCaseDeny.isNotEmpty()) {
                    item {
                        Text(
                            text = "Today Case Deny",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                // 🔹 Today Case Deny list (ONLY ONCE)
                items(todayCaseDeny) { item ->
                    CaseDenyCard(
                        item = item,
                        onClick = {
                            if (!item.isExpired) {
                                selectedCase = item
                            }
                        }
                    )
                }
            }
        }
    }

    /* ---------------- BOTTOM SHEET ---------------- */
    selectedCase?.let {
        CaseDenyBottomSheet(
            item = it,
            onDismiss = { selectedCase = null }
        )
    }

    /* ---------------- LOGOUT ---------------- */
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        session.logout()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                }) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

