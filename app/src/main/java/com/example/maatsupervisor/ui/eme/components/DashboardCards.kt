package com.example.maatsupervisor.ui.eme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.model.DashboardSummary

@Composable
fun DashboardCards(summary: DashboardSummary) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        /* 🔹 TOTAL VEHICLE */
        HighlightCard(
            title = "Total Vehicles ",
            value = summary.totalVehicle.toString()
        )

        /* 🔹 VEHICLE TYPES */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MiniCard("JSSK", summary.jssk, Color(0xFF2E7D32), Modifier.weight(1f))
            MiniCard("Backup", summary.backup, Color(0xFFEF6C00), Modifier.weight(1f))
            MiniCard("VIP", summary.vip, Color(0xFF6A1B9A), Modifier.weight(1f))
        }

        /* 🔹 ACTION CARDS */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MiniCard(
                "Case Deny\n30 Days",
                summary.totalCaseDeny30,
                Color(0xFFD32F2F),
                Modifier.weight(1f)
            )
            MiniCard(
                "Your Action",
                summary.yourAction,
                Color(0xFF1565C0),
                Modifier.weight(1f)
            )
        }
    }
}

/* ----------------- COMPONENTS ----------------- */

@Composable
private fun HighlightCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun MiniCard(
    title: String,
    value: Int,
    color: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color.copy(alpha = 0.10f))
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}
