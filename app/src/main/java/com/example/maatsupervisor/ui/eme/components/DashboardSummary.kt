package com.example.maatsupervisor.ui.eme.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.model.VehicleDashboard

@Composable
fun DashboardSummary(vehicles: List<VehicleDashboard>) {

//    val totalToday = vehicles.sumOf { it.todayCase }
//    val totalMonth = vehicles.sumOf { it.monthCase }
//    val totalDeny = vehicles.sumOf { it.monthServiceDeny }
//    val totalUad = vehicles.sumOf { it.monthUad }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            SummaryItem("Today", totalToday)
//            SummaryItem("Month", totalMonth)
//            SummaryItem("Deny", totalDeny)
//            SummaryItem("UAD", totalUad)
        }
    }
}

@Composable
private fun SummaryItem(label: String, value: Int) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value.toString(), style = MaterialTheme.typography.titleLarge)
    }
}
