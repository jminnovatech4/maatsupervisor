package com.example.maatsupervisor.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.model.DashboardSummary

@Composable
fun DashboardHeader(summary: DashboardSummary) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            /* ----------- TOP ROW ----------- */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text("Total Vehicle : ${summary.totalVehicle}",
                        style = MaterialTheme.typography.titleMedium)

                    Spacer(Modifier.height(6.dp))
                    Text("JSSK : ${summary.jssk}")
                    Text("Backup : ${summary.backup}")
                    Text("VIP : ${summary.vip}")
                }

                Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                    Text(
                        "Total case deny\n(last 30 days)",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        summary.totalCaseDeny30.toString(),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Divider(Modifier.padding(vertical = 12.dp))

            /* ----------- YOUR ACTION ----------- */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Your Action",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    summary.yourAction.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
