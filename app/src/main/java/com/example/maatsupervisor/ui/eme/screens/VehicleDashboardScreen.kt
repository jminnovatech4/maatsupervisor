package com.example.maatsupervisor.ui.eme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.UserSession
import com.example.maatsupervisor.data.model.DashboardSummary
import com.example.maatsupervisor.data.model.VehicleDashboard
import com.example.maatsupervisor.network.CaseDenyApi
import com.example.maatsupervisor.network.DashboardApi
import com.example.maatsupervisor.network.DashboardSummaryApi
import com.example.maatsupervisor.ui.eme.screens.components.VehicleCard

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun VehicleDashboardScreen() {

    val context = LocalContext.current
    val session = remember { UserSession(context) }
    val scope = rememberCoroutineScope()

    var vehicles by remember { mutableStateOf<List<VehicleDashboard>>(emptyList()) }
    var search by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    // 🔴 ONE dialog state for whole screen
    var selectedVehicle by remember { mutableStateOf<VehicleDashboard?>(null) }

    LaunchedEffect(Unit) {
        val gid = session.getGid()

        // 1️⃣ Fetch all vehicles
        DashboardApi.fetchVehicles(gid) { vSuccess, vehicleList ->
            if (!vSuccess) {
                scope.launch(Dispatchers.Main) { loading = false }
                return@fetchVehicles
            }

            // 2️⃣ Fetch today deny list
            CaseDenyApi.fetchTodayCaseDeny(gid) { _, denyList ->

                scope.launch(Dispatchers.Main) {

                    val denyMap = denyList.associateBy { it.vehicleNo }

                    // 3️⃣ Merge data
                    vehicles = vehicleList.map { vehicle ->
                        val deny = denyMap[vehicle.vehicleNo]
                        vehicle.copy(
                            todayDenyCount = deny?.todayCount ?: 0
                        )
                    }

                    loading = false // ✅ IMPORTANT
                }
            }
        }
    }

    val filtered = vehicles.filter {
        it.vehicleNo.contains(search, true) ||
                it.baseLocation.contains(search, true) ||
                it.type.contains(search, true)
    }

    Column {

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Search vehicle / location") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

        var summary by remember { mutableStateOf<DashboardSummary?>(null) }

        LaunchedEffect(Unit) {
            val emeGid = session.getGid()   // e.g. "223368"

            DashboardSummaryApi.fetchSummary(emeGid) { result ->
                summary = result
            }
        }



        when {
            loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                LazyColumn {
                    items(filtered) { vehicle ->
                        VehicleCard(
                            vehicle = vehicle,
                            onDenyClick = { selectedVehicle = it }
                        )
                    }
                }
            }
        }
    }

    // 🔴 GLOBAL ACTION SHEET

}

