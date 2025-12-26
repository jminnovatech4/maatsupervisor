package com.example.maatsupervisor.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.model.VehicleDashboard

@Composable
fun VehicleCard(
    vehicle: VehicleDashboard,
    onDenyClick: (VehicleDashboard) -> Unit
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            /* ---------------- HEADER ---------------- */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = vehicle.vehicleNo,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "${vehicle.dist} • ${vehicle.type}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                AlertBadges(
                    vehicle = vehicle,
                    onDenyClick = { onDenyClick(vehicle) }
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Base: ${vehicle.baseLocation}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(12.dp))

            /* ---------------- ACTIONS ---------------- */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TextButton(onClick = {
                    val intent = Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:${vehicle.mobile}")
                    )
                    context.startActivity(intent)
                }) {
                    Text("Call: ${vehicle.mobile}")
                }

                TextButton(onClick = {
                    val mapIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=${Uri.encode(vehicle.baseLocation)}")
                    )
                    context.startActivity(mapIntent)
                }) {
                    Text("Map")
                }
            }
        }
    }
}

/* ---------------- ALERT BADGES ---------------- */

@Composable
private fun AlertBadges(
    vehicle: VehicleDashboard,
    onDenyClick: () -> Unit
) {
    Row {

        // 🔴 TODAY CASE DENY BADGE
        if (vehicle.todayDenyCount > 0) {
            AssistChip(
                onClick = onDenyClick,
                label = { Text("Deny ${vehicle.todayDenyCount}") },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    labelColor = MaterialTheme.colorScheme.onErrorContainer
                )
            )
        }
    }
}
